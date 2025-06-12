package dev.sideproject.ndx2.security;

import dev.sideproject.ndx2.entity.Account;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

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
                    throw new AppException(errorCode);
                });
        log.info("account: username={}, password={}, role = {} ", username, account.getPassword(), account.getRole());
        return new CustomUserDetails(account);
    }
}
