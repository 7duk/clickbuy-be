package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.constant.TokenType;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.repository.AccountRepository;
import dev.sideproject.ndx2.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
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
    public String createToken(Long id, String email, TokenType tokenType, LocalDateTime issuedAt, LocalDateTime expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, id);
        claims.put(Claims.AUDIENCE, email);
        claims.put(Claims.ID, UUID.randomUUID());
        claims.put(CLAIM_TOKEN_TYPE, tokenType);
        claims.put(Claims.ISSUED_AT, issuedAt);
        claims.put(Claims.ISSUER, jwtIssuer);
        claims.put(Claims.EXPIRATION, expiration);
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public boolean isUsed(String token) {
        Claims claims = extractClaims(token);
        UUID jit = claims.get(Claims.ID, UUID.class);
        if (Objects.isNull(jit)) {
            log.error(ErrorCode.JTI_IS_NULL.getMessage());
            throw new AppException(ErrorCode.JTI_IS_NULL);
        }
        return isContain(jit);
    }

    @Override
    public boolean isExpired(String token) {
        Claims claims = extractClaims(token);
        LocalDateTime expiration = claims.get(Claims.EXPIRATION, LocalDateTime.class);
        return expiration.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean pushToBlackList(UUID jit) {
        return backListJIT.add(jit);
    }

    public boolean isContain(UUID jit) {
        return backListJIT.contains(jit);
    }

    @Override
    public boolean isValid(String token) {
        Claims claims = extractClaims(token);

        if (isInvalidTokenType(claims)) return false;
        if (!isValidIssuer(claims)) return false;
        if (!isExistingAccount(claims)) return false;

        return true;
    }

    private boolean isInvalidTokenType(Claims claims) {
        return Arrays.stream(TokenType.values())
                .anyMatch(value -> value.equals(claims.get(CLAIM_TOKEN_TYPE)));
    }

    private boolean isValidIssuer(Claims claims) {
        return jwtIssuer.equals(claims.get(Claims.ISSUER, String.class));
    }

    private boolean isExistingAccount(Claims claims) {
        String id = claims.get(Claims.SUBJECT, String.class);
        String email = claims.get(Claims.AUDIENCE, String.class);
        return accountRepository.existsByIdAndEmail(Long.parseLong(id), email);
    }

}
