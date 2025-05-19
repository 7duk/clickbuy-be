package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.constant.TokenType;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TokenService {
    String createToken(Long id, String email, TokenType tokenType,
                       int expirationHours);
    Claims extractClaims(String token);
    <T> T getClaim(String token, String key, Class<T> requiredType);
    boolean pushToBlackList(UUID token);
    boolean isValid(String token);
}
