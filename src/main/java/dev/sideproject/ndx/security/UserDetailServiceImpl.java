package dev.sideproject.ndx.security;

import dev.sideproject.ndx.entity.Account;
import dev.sideproject.ndx.exception.AppException;
import dev.sideproject.ndx.repository.AccountRepository;
import dev.sideproject.ndx.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.ACCOUNT_DOES_NOT_EXIST;
                    log.error("{} with username: {} provided", errorCode.getMessage(), username);
                    return new AppException(errorCode);
                });
        log.info("account: username={}, password={}, role = {} ", username, account.getPassword(), account.getRole());
        return new CustomUserDetails(account);
    }
}
