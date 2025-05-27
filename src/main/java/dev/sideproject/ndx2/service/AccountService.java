package dev.sideproject.ndx2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.AccountDto;

public interface AccountService {
    AccountDto register(AccountDto accountDto) throws JsonProcessingException;
    void verify(String token);
}
