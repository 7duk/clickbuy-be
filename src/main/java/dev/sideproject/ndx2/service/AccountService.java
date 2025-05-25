package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.dto.AccountDto;

public interface AccountService {
    AccountDto register(AccountDto accountDto);
    void verify(String token);
}
