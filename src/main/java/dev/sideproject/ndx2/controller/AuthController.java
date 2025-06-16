package dev.sideproject.ndx2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.*;
import dev.sideproject.ndx2.dto.request.AuthRequest;
import dev.sideproject.ndx2.dto.request.LoginRequest;
import dev.sideproject.ndx2.dto.request.RegisterRequest;
import dev.sideproject.ndx2.dto.response.AccountResponse;
import dev.sideproject.ndx2.dto.response.AuthResponse;
import dev.sideproject.ndx2.helper.TokenHelper;
import dev.sideproject.ndx2.service.AuthService;
import dev.sideproject.ndx2.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthController {
    final AuthService authService;
    final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws JsonProcessingException {
        AccountResponse accountDtoCreated = authService.register(registerRequest);
        int code = HttpStatus.CREATED.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("created successfully").data(accountDtoCreated)
                .code(code).build();
        return ResponseEntity.status(code).body(successResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                                 HttpServletResponse response) {
        AuthResponse authResponse = authService.login(loginRequest);
        Cookie cookie = new Cookie("refresh_token", authResponse.getRefreshToken());
        cookie.setMaxAge(24 * 30 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        cookie.setAttribute("SameSite", "Strict");
        int code = HttpStatus.OK.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("login successfully").data(authResponse)
                .code(code).build();
        return ResponseEntity.status(code).body(successResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse> logout(@CookieValue("refresh_token") String refreshToken, HttpServletRequest httpServletRequest) {
        String accessToken = TokenHelper.getAccessToken(httpServletRequest);

        if (accessToken != null) {
            UUID uuidFromAccessToken = UUID.fromString(tokenService.getClaim(accessToken, Claims.ID, String.class));
            tokenService.pushToBlackList(uuidFromAccessToken);
        }
        if (refreshToken != null) {
            UUID uuidFromRefreshToken = UUID.fromString(tokenService.getClaim(refreshToken, Claims.ID, String.class));
            tokenService.pushToBlackList(uuidFromRefreshToken);
        }

        int code = HttpStatus.OK.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("logout successfully")
                .code(code).build();
        return ResponseEntity.status(code).body(successResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue("refresh_token") String refreshToken, @RequestBody @Valid AuthRequest authRequest, HttpServletResponse httpResponse) {
        String accessToken = authRequest.getAccessToken();
        AuthResponse authResponse = authService.refreshToken(refreshToken, accessToken);
        Cookie cookie = new Cookie("refresh_token", authResponse.getRefreshToken());
        cookie.setMaxAge(24 * 30 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "Strict");
        httpResponse.addCookie(cookie);
        int code = HttpStatus.OK.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("refresh token successfully").data(authResponse)
                .code(code).build();
        return ResponseEntity.status(code).body(successResponse);
    }

}
