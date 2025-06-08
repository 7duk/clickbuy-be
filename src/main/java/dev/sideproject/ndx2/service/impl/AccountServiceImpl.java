package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.dto.AccountResponse;
import dev.sideproject.ndx2.constant.Role;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.service.AccountService;
import dev.sideproject.ndx2.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    TokenService tokenService;

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
                        .createdBy(account.getCreatedBy().getId())
                        .lastModifiedAt(account.getUpdatedAt())
                        .lastModifiedBy(Objects.isNull(account.getUpdatedBy())
                                ? null : account.getUpdatedBy().getId())
                        .build()
                ).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_DOES_NOT_EXIST));
    }

}
