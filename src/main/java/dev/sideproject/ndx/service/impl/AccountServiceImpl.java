package dev.sideproject.ndx.service.impl;

import dev.sideproject.ndx.common.Role;
import dev.sideproject.ndx.dto.response.AccountResponse;
import dev.sideproject.ndx.entity.Account;
import dev.sideproject.ndx.service.AccountService;
import dev.sideproject.ndx.dto.request.AccountRequest;
import dev.sideproject.ndx.exception.AppException;
import dev.sideproject.ndx.exception.ErrorCode;
import dev.sideproject.ndx.repository.AccountRepository;
import dev.sideproject.ndx.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    TokenService tokenService;
    PasswordEncoder passwordEncoder;

    @Override
    public void verify(String token) {
        if (tokenService.isValid(token)) {
            ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
            log.error("{} with token value '{}' ", errorCode.getMessage(), token);
            throw new AppException(errorCode);
        }
        String username = tokenService.getClaim(token, Claims.SUBJECT, String.class);

        accountRepository.findByUsername(username).ifPresentOrElse((account -> {
            if (!Objects.equals(account.getRole(), Role.GUEST)) {
                ErrorCode errorCode = ErrorCode.TOKEN_INVALID;
                log.error(errorCode.getMessage());
                throw new AppException(errorCode);
            }
            accountRepository.updateRoleById(account.getId(), Role.USER);
            UUID jti = tokenService.getClaim(token, Claims.ID, UUID.class);
            tokenService.pushToBlackList(jti);
        }), () -> {
            ErrorCode errorCode = ErrorCode.ACCOUNT_DOES_NOT_EXIST;
            log.error("{} with username:{}", errorCode.getMessage(), username);
            throw new AppException(errorCode);
        });
    }

    @Transactional(readOnly = true)
    @Override
    public AccountResponse details(Integer id) {
        return accountRepository.findById(id).
                map(account -> AccountResponse.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .fullName(account.getFullName())
                        .email(account.getEmail())
                        .createdAt(account.getCreatedAt())
                        .createdBy(account.getCreatedBy())
                        .lastModifiedAt(account.getUpdatedAt())
                        .lastModifiedBy(Objects.isNull(account.getUpdatedBy())
                                ? null : account.getUpdatedBy())
                        .build()
                ).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_DOES_NOT_EXIST));
    }

    @Transactional
    @Override
    public void changePassword(Integer accountId, AccountRequest accountRequest) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> {
            throw new AppException(ErrorCode.ACCOUNT_DOES_NOT_EXIST);
        });
        if (!passwordEncoder.matches(accountRequest.getOldPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }
        String newPasswordEncoded = passwordEncoder.encode(accountRequest.getNewPassword());
        account.setPassword(newPasswordEncoded);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void editProfile(Integer accountId, AccountRequest accountRequest) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_DOES_NOT_EXIST));
        account.setFullName(accountRequest.getFullName());
        accountRepository.saveAndFlush(account);
    }
}
