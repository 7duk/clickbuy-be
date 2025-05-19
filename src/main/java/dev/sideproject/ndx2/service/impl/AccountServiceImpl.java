package dev.sideproject.ndx2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.constant.TokenType;
import dev.sideproject.ndx2.dto.AccountResponse;
import dev.sideproject.ndx2.dto.AuthResponse;
import dev.sideproject.ndx2.dto.LoginRequest;
import dev.sideproject.ndx2.dto.RegisterRequest;
import dev.sideproject.ndx2.entity.Account;
import dev.sideproject.ndx2.constant.Role;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.handler.RabbitMQPublisher;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.service.AccountService;
import dev.sideproject.ndx2.service.BaseService;
import dev.sideproject.ndx2.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl extends BaseService<Account, Long> implements AccountService {
    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;
    TokenService tokenService;
    RabbitMQPublisher rabbitMQPublisher;

    public AccountServiceImpl(PasswordEncoder passwordEncoder, AccountRepository accountRepository,
                              TokenService tokenService, RabbitMQPublisher rabbitMQPublisher) {
        super(accountRepository);
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.tokenService = tokenService;
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

    @Override
    public AccountResponse register(RegisterRequest registerRequest) throws JsonProcessingException {
        String passwordHashed = passwordEncoder.encode(registerRequest.getPassword());

        Account createdByAccount = accountRepository.findById(1L)
                .orElse(new Account());

        Account accountMapped = Account.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getUsername())
                .password(passwordHashed)
                .createdBy(createdByAccount)
                .role(Role.GUEST).build();

        Optional<Account> accountSavedOptional = save(accountMapped);

        if (accountSavedOptional.isPresent()) {
            Account accountSaved = accountSavedOptional.get();
            AccountResponse accountDtoResponse = AccountResponse.builder()
                    .id(accountSaved.getId())
                    .username(accountSaved.getUsername())
                    .fullName(accountSaved.getFullName())
                    .email(accountSaved.getEmail())
                    .createdAt(accountSaved.getCreatedAt())
                    .createdBy(accountSaved.getCreatedBy().getId())
                    .lastModifiedAt(accountSaved.getUpdatedAt())
                    .lastModifiedBy(Objects.isNull(accountSaved.getUpdatedBy()) ? null : accountSaved.getUpdatedBy().getId())
                    .build();
            rabbitMQPublisher.sendMessage(UUID.randomUUID().toString(), accountDtoResponse);
            return accountDtoResponse;
        } else {
            ErrorCode errorCode = ErrorCode.REGISTER_FAILED;
            log.error("account {} - account not saved", errorCode.getMessage());
            throw new AppException(errorCode);
        }
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Account account = accountRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.ACCOUNT_DOES_NOT_EXIST;
                    log.error("{} with username: {}", errorCode.getMessage(), loginRequest.getUsername());
                    throw new AppException(errorCode);
                });
        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
            ErrorCode errorCode = ErrorCode.PASSWORD_INVALID;
            log.error("{} with username: {} and password: {} provided", errorCode.getMessage(), loginRequest.getUsername(), loginRequest.getPassword());
            throw new AppException(errorCode);
        }
        String accessToken = tokenService.createToken(account.getId(), account.getEmail(), TokenType.ACCESS, 1);
        String refreshToken = tokenService.createToken(account.getId(), account.getEmail(), TokenType.REFRESH, 24);
        AuthResponse authResponse = AuthResponse.builder().accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(account.getId())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .email(account.getEmail())
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy().getId())
                .lastModifiedAt(account.getUpdatedAt())
                .lastModifiedBy(Objects.isNull(account.getUpdatedBy()) ? null : account.getUpdatedBy().getId())
                .build();
        return authResponse;
    }

    @Override
    public void verify(String token) {
        if (tokenService.isValid(token)) {
            ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
            log.error("{} with token value '{}' ", errorCode.getMessage(), token);
            throw new AppException(errorCode);
        }
        Long id = tokenService.getClaim(token, Claims.SUBJECT, Long.class);
        String email = tokenService.getClaim(token, Claims.AUDIENCE, String.class);
        if (accountRepository.existsByIdAndEmail(id, email)) {
            Account account = accountRepository.findById(id).get();
            if (!Objects.equals(account.getRole(), Role.GUEST)) {
                ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
                log.error(errorCode.getMessage());
                throw new AppException(errorCode);
            }
            accountRepository.updateRoleById(id, Role.USER);
            UUID jti = tokenService.getClaim(token, Claims.ID, UUID.class);
            tokenService.pushToBlackList(jti);
        } else {
            ErrorCode errorCode = ErrorCode.ACCOUNT_DOES_NOT_EXIST;
            log.error("{} with id:{} - mail{}", errorCode.getMessage(), id, email);
            throw new AppException(errorCode);
        }
    }
}
