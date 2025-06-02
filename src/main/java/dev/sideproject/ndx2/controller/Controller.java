package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.dto.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class Controller {
    protected ResponseEntity<SuccessResponse> response(HttpStatus httpStatus, String message, Object data) {
        int code = httpStatus.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .data(data).code(code)
                .message(message).build();
        return ResponseEntity.status(code).body(successResponse);
    }
}
