package dev.sideproject.ndx2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.constant.Role;
import dev.sideproject.ndx2.constant.TokenType;
import dev.sideproject.ndx2.dto.AccountResponse;
import dev.sideproject.ndx2.dto.AuthResponse;
import dev.sideproject.ndx2.dto.LoginRequest;
import dev.sideproject.ndx2.dto.RegisterRequest;
import dev.sideproject.ndx2.entity.Account;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.handler.RabbitMQPublisher;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.service.AuthService;
import dev.sideproject.ndx2.service.TokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    RabbitMQPublisher rabbitMQPublisher;
    DaoAuthenticationProvider daoAuthenticationProvider;
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    TokenService tokenService;

    @Transactional
    @Override
    public AccountResponse register(RegisterRequest registerRequest) throws JsonProcessingException {
        String passwordHashed = passwordEncoder.encode(registerRequest.getPassword());

        Account createdByAccount = accountRepository.findById(1)
                .orElse(new Account());

        Account accountMapped = Account.builder()
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getUsername())
                .password(passwordHashed)
                .createdBy(createdByAccount.getCreatedBy())
                .role(Role.GUEST).build();

        Account accountSaved = accountRepository.save(accountMapped);
        AccountResponse accountDtoResponse = AccountResponse.builder()
                .id(accountSaved.getId())
                .username(accountSaved.getUsername())
                .fullName(accountSaved.getFullName())
                .email(accountSaved.getEmail())
                .createdAt(accountSaved.getCreatedAt())
                .createdBy(accountSaved.getId())
                .lastModifiedAt(accountSaved.getUpdatedAt())
                .lastModifiedBy(Objects.isNull(accountSaved.getUpdatedBy()) ? null : accountSaved.getUpdatedBy())
                .build();
        rabbitMQPublisher.sendMessage(UUID.randomUUID().toString(), accountDtoResponse);
        return accountDtoResponse;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication auth = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        Account account = accountRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.ACCOUNT_DOES_NOT_EXIST;
                    log.error("{} with username: {}", errorCode.getMessage(), loginRequest.getUsername());
                    throw new AppException(errorCode);
                });

        String accessToken = tokenService.createToken(account.getUsername(), TokenType.ACCESS, 1);
        String refreshToken = tokenService.createToken(account.getUsername(), TokenType.REFRESH, 24 * 30);
        AuthResponse authResponse = AuthResponse.builder().accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(account.getId())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .email(account.getEmail())
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy())
                .lastModifiedAt(account.getUpdatedAt())
                .lastModifiedBy(Objects.isNull(account.getUpdatedBy()) ? null : account.getId())
                .build();
        return authResponse;
    }
}
