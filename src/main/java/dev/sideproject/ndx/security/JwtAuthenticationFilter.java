package dev.sideproject.ndx.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sideproject.ndx.dto.ErrorResponse;
import dev.sideproject.ndx.exception.ErrorCode;
import dev.sideproject.ndx.common.TokenUtils;
import dev.sideproject.ndx.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    TokenService tokenService;
    UserDetailServiceImpl userDetailService;
    ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = TokenUtils.getAccessToken(request);
        if (StringUtils.isNotBlank(accessToken)) {
            if (tokenService.isValid(accessToken)) {
                String username = tokenService.getClaim(accessToken, Claims.SUBJECT, String.class);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                log.info("User Authority: {}", userDetails.getAuthorities());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                handleAuthenticationError(response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void handleAuthenticationError(HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.UN_AUTHENTICATED.getHttpStatus().value())
                .message(ErrorCode.UN_AUTHENTICATED.getMessage()).build();

        response.setContentType("application/json");
        response.setStatus(ErrorCode.UN_AUTHENTICATED.getHttpStatus().value());

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
