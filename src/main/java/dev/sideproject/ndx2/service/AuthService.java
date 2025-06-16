package dev.sideproject.ndx2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.response.AccountResponse;
import dev.sideproject.ndx2.dto.response.AuthResponse;
import dev.sideproject.ndx2.dto.request.LoginRequest;
import dev.sideproject.ndx2.dto.request.RegisterRequest;

public interface AuthService {
    AccountResponse register(RegisterRequest registerRequest) throws JsonProcessingException;
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(String refreshToken, String accessToken);
}
