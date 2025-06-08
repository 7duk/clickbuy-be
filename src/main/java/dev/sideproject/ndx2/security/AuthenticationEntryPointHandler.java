package dev.sideproject.ndx2.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sideproject.ndx2.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import dev.sideproject.ndx2.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
   final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("authentication exception: {}", authException.getMessage());
        ErrorCode errorCode= getErrorCodeByException(authException);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage()).build();

        response.setContentType("application/json");
        response.setStatus(errorCode.getHttpStatus().value());

        objectMapper.writeValue(response.getOutputStream(), errorResponse);

    }

    private ErrorCode getErrorCodeByException(AuthenticationException authException) {
        if (authException instanceof BadCredentialsException) {
            return ErrorCode.UN_AUTHENTICATED;
        } else if (authException instanceof InsufficientAuthenticationException) {
            return ErrorCode.TOKEN_INVALID;
        } else if (authException instanceof AccountExpiredException) {
            return ErrorCode.TOKEN_INVALID;
        }  else if (authException instanceof UsernameNotFoundException) {
            return ErrorCode.ACCOUNT_DOES_NOT_EXIST;
        }
        return ErrorCode.UN_AUTHENTICATED;
    }
}
