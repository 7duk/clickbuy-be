package dev.sideproject.ndx.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx.dto.request.LoginRequest;
import dev.sideproject.ndx.dto.request.RegisterRequest;
import dev.sideproject.ndx.dto.response.AccountResponse;
import dev.sideproject.ndx.dto.response.AuthResponse;

public interface AuthService {
    AccountResponse register(RegisterRequest registerRequest) throws JsonProcessingException;

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refreshToken(String refreshToken, String accessToken);
}
