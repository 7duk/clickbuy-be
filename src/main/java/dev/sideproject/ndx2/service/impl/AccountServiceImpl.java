package dev.sideproject.ndx2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.AccountDto;
import dev.sideproject.ndx2.entity.Account;
import dev.sideproject.ndx2.constant.Role;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.handler.RabbitMQPublisher;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.service.AccountService;
import dev.sideproject.ndx2.service.BaseService;
import dev.sideproject.ndx2.service.TokenService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public AccountDto register(AccountDto accountDto) throws JsonProcessingException {
        String passwordHashed = passwordEncoder.encode(accountDto.getPassword());

        Account createdByAccount = accountRepository.findById(1L)
                .orElse(new Account());

        Account accountMapped = Account.builder()
                .email(accountDto.getEmail())
                .fullName(accountDto.getFullName())
                .username(accountDto.getUsername())
                .password(passwordHashed)
                .createdBy(createdByAccount)
                .role(Role.GUEST).build();

        Optional<Account> accountSavedOptional = save(accountMapped);

        if (accountSavedOptional.isPresent()) {
            Account accountSaved = accountSavedOptional.get();
            AccountDto accountDtoResponse = AccountDto.builder()
                    .id(accountSaved.getId())
                    .username(accountSaved.getUsername())
                    .fullName(accountSaved.getFullName())
                    .email(accountSaved.getEmail())
                    .createdAt(accountSaved.getCreatedAt())
                    .createdBy(accountSaved.getCreatedBy().getId())
                    .lastModifiedAt(accountSaved.getUpdatedAt())
                    .lastModifiedBy(Objects.isNull(accountSaved.getUpdatedBy()) ? null : accountSaved.getUpdatedBy().getId())
                    .build();
            rabbitMQPublisher.sendMessage(UUID.randomUUID().toString(),accountDtoResponse);
            return accountDtoResponse;
        } else {
            ErrorCode errorCode = ErrorCode.REGISTER_FAILED;
            log.error("Account {} - account not saved", errorCode.getMessage());
            throw new AppException(errorCode);
        }
    }

    @Override
    public void verify(String token) {

    }
}
