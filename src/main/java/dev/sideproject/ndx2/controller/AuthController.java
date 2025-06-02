package dev.sideproject.ndx2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.*;
import dev.sideproject.ndx2.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthController {
    final AuthService authService;

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
        int code = HttpStatus.OK.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("login successfully").data(authResponse)
                .code(code).build();
        return ResponseEntity.status(code).body(successResponse);
    }
}
