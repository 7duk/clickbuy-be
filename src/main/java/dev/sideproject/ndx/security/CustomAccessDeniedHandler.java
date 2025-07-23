package dev.sideproject.ndx.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sideproject.ndx.dto.ErrorResponse;
import dev.sideproject.ndx.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error("authorize exception: {}", accessDeniedException.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder().
                code(ErrorCode.FORBIDDEN.getHttpStatus().value())
                .message(ErrorCode.FORBIDDEN.getMessage()).build();

        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
