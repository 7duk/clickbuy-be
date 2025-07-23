package dev.sideproject.ndx.exception;

import dev.sideproject.ndx.dto.ErrorResponse;
import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorResponse toResponse() {
       return ErrorResponse.builder().
                message(errorCode.getMessage()).
                code(errorCode.getHttpStatus().value()).build();
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
