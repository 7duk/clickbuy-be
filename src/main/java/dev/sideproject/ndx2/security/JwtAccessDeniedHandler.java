package dev.sideproject.ndx2.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import dev.sideproject.ndx2.exception.ErrorCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Authorize exception: {}", accessDeniedException.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ErrorCode.UN_AUTHORIZED.getHttpStatus(), accessDeniedException.getMessage());
        problemDetail.setTitle("Application Exception");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errors", List.of(accessDeniedException.getMessage()));

        int statusCode = problemDetail.getStatus();

        response.setContentType("application/json");
        response.setStatus(statusCode);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), problemDetail);
        } catch (Exception e) {
            log.error("Error writing response", e);
            response.sendError(statusCode, accessDeniedException.getMessage());
        }
    }
}
