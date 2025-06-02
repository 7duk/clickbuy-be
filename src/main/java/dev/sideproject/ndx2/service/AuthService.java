package dev.sideproject.ndx2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.AccountResponse;
import dev.sideproject.ndx2.dto.AuthResponse;
import dev.sideproject.ndx2.dto.LoginRequest;
import dev.sideproject.ndx2.dto.RegisterRequest;

public interface AuthService {
    AccountResponse register(RegisterRequest registerRequest) throws JsonProcessingException;
    AuthResponse login(LoginRequest loginRequest);
}
