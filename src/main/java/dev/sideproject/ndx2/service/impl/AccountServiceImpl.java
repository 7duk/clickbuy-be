package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.dto.AccountDto;
import dev.sideproject.ndx2.entity.Account;
import dev.sideproject.ndx2.constant.Role;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.service.AccountService;
import dev.sideproject.ndx2.service.BaseService;
import dev.sideproject.ndx2.service.TokenService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl extends BaseService<Account, Long> implements AccountService {
    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;
    TokenService tokenService;

    public AccountServiceImpl(PasswordEncoder passwordEncoder, AccountRepository accountRepository, TokenService tokenService) {
        super(accountRepository);
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.tokenService = tokenService;
    }

    @Override
    public AccountDto register(AccountDto accountDto) {
        String passwordHashed = passwordEncoder.encode(accountDto.getPassword());
        Account accountMapped = Account.builder()
                .email(accountDto.getEmail())
                .fullName(accountDto.getFullName())
                .username(accountDto.getUsername())
                .password(passwordHashed)
                .role(Role.GUEST).build();

        Optional<Account> accountSavedOptional = save(accountMapped);

        if (accountSavedOptional.isPresent()) {
            Account accountSaved = accountSavedOptional.get();
            return AccountDto.builder()
                    .id(accountSaved.getId())
                    .username(accountSaved.getUsername())
                    .fullName(accountSaved.getFullName())
                    .email(accountSaved.getEmail())
                    .createdAt(LocalDateTime.ofInstant(accountSaved.getCreatedAt(), ZoneId.systemDefault()))
                    .createdBy(accountSaved.getCreatedBy().getId())
                    .lastModifiedAt(LocalDateTime.ofInstant(accountSaved.getUpdatedAt(), ZoneId.systemDefault()))
                    .lastModifiedBy(accountSaved.getUpdatedBy().getId())
                    .build();
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
