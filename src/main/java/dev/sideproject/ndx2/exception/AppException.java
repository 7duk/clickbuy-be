package dev.sideproject.ndx2.exception;

import dev.sideproject.ndx2.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorResponse toResponse() {
        ErrorResponse errorResponse = ErrorResponse.builder().
                message(errorCode.getMessage().toUpperCase()).
                code(errorCode.getHttpStatus().value()).build();
        return errorResponse;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
