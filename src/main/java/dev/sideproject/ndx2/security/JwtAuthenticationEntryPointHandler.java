package dev.sideproject.ndx2.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import dev.sideproject.ndx2.exception.ErrorCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Authentication exception: {}", authException.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ErrorCode.UN_AUTHENTICATED.getHttpStatus(), authException.getMessage());
        problemDetail.setTitle("Application Exception");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errors", List.of(authException.getMessage()));

        int statusCode = problemDetail.getStatus();

        response.setContentType("application/json");
        response.setStatus(statusCode);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), problemDetail);
        } catch (Exception e) {
            log.error("Error writing response", e);
            response.sendError(statusCode, authException.getMessage());
        }
    }
}
