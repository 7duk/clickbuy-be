package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.constant.TokenType;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenServiceImpl implements TokenService {
    final SecretKey secretKey;

    static final String CLAIM_TOKEN_TYPE = "token_type";

    @Qualifier(value = "blackList")
    final Set<UUID> backListJIT;

    @Value("${jwt.issuer}")
    String jwtIssuer;

    final AccountRepository accountRepository;

    @Override
    public String createToken(String username, TokenType tokenType, int expirationHours) {
        ZonedDateTime now = ZonedDateTime.now();
        Instant issuedAt = now.toInstant();
        Instant expireAt = now.plusHours(expirationHours).toInstant();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, username);
        claims.put(Claims.ID, UUID.randomUUID().toString());
        claims.put(CLAIM_TOKEN_TYPE, tokenType);
        claims.put(Claims.ISSUED_AT, Date.from(issuedAt));
        claims.put(Claims.ISSUER, jwtIssuer);
        claims.put(Claims.EXPIRATION, Date.from(expireAt));
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }

    @Override
    public String createToken(Map<String, Object> claims){
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }

    @Override
    public <T> T getClaim(String token, String key, Class<T> requiredType) {
        Claims claims = extractClaims(token);
        return switch (key) {
            case Claims.SUBJECT -> claims.get(Claims.SUBJECT, requiredType);
            case Claims.ID -> claims.get(Claims.ID, requiredType);
            case Claims.ISSUED_AT -> claims.get(Claims.ISSUED_AT, requiredType);
            case Claims.ISSUER -> claims.get(Claims.ISSUER, requiredType);
            case Claims.EXPIRATION -> claims.get(Claims.EXPIRATION, requiredType);
            case CLAIM_TOKEN_TYPE -> claims.get(CLAIM_TOKEN_TYPE, requiredType);
            default -> throw new IllegalStateException("unexpected value: " + key);
        };
    }

    @Override
    public Claims extractClaims(String token) {
        try{
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        }
        catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    @Override
    public boolean pushToBlackList(UUID jit) {
        return backListJIT.add(jit);
    }

    @Override
    public boolean isValid(String token) {
        Claims claims = extractClaims(token);
        if (isInvalidTokenType(claims)) return false;
        if (!isValidIssuer(claims)) return false;
        if (isExpired(token)) return false;
        if (isUsed(token)) return false;
        return true;
    }

    public boolean isContain(UUID jit) {
        return backListJIT.contains(jit);
    }

    public boolean isUsed(String token) {
        Claims claims = extractClaims(token);
        try {
            UUID jit = UUID.fromString(claims.get(Claims.ID, String.class));
            return isContain(jit);
        }
        catch (IllegalArgumentException e) {
            log.error(ErrorCode.JTI_IS_INVALID.getMessage());
            throw new AppException(ErrorCode.JTI_IS_INVALID);
        }
    }

    public boolean isExpired(String token) {
        Claims claims = extractClaims(token);
        Date expiration = claims.get(Claims.EXPIRATION, Date.class);
        return expiration.before(Date.from(Instant.now()));
    }

    private boolean isInvalidTokenType(Claims claims) {
        return Arrays.stream(TokenType.values())
                .anyMatch(value -> value.equals(claims.get(CLAIM_TOKEN_TYPE)));
    }

    private boolean isValidIssuer(Claims claims) {
        return jwtIssuer.equals(claims.get(Claims.ISSUER, String.class));
    }


}
