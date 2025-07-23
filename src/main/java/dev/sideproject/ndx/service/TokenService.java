package dev.sideproject.ndx.service;

import dev.sideproject.ndx.common.TokenType;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.UUID;

public interface TokenService {
    String createToken(String username, TokenType tokenType,
                       int expirationHours);

    String createToken(Map<String, Object> claims);

    Claims extractClaims(String token);

    <T> T getClaim(String token, String key, Class<T> requiredType);

    void pushToBlackList(UUID token);

    boolean isValid(String token);
}
