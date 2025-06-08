package dev.sideproject.ndx2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.AccountResponse;
import dev.sideproject.ndx2.dto.AuthResponse;
import dev.sideproject.ndx2.dto.LoginRequest;
import dev.sideproject.ndx2.dto.RegisterRequest;

public interface AccountService {
    void verify(String token);
    AccountResponse details(Integer id);
}
