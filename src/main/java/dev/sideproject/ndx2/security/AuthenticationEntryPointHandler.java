package dev.sideproject.ndx2.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sideproject.ndx2.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import dev.sideproject.ndx2.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
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
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.ACCOUNT_DOES_NOT_EXIST.getHttpStatus().value())
                .message(ErrorCode.ACCOUNT_DOES_NOT_EXIST.getMessage()).build();

        response.setContentType("application/json");
        response.setStatus(ErrorCode.ACCOUNT_DOES_NOT_EXIST.getHttpStatus().value());

        objectMapper.writeValue(response.getOutputStream(), errorResponse);

    }
}
