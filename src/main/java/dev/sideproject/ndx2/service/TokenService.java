package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.constant.TokenType;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TokenService {
    String createToken(Long id, String email, TokenType tokenType,
                       LocalDateTime issuedAt, LocalDateTime expiration);
    Claims extractClaims(String token);
    boolean isUsed(String token);
    boolean isExpired(String token);
    boolean pushToBlackList(UUID token);
    boolean isValid(String token);
}
