package dev.sideproject.ndx2.advice;

import dev.sideproject.ndx2.dto.ValidationError;
import dev.sideproject.ndx2.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult().getFieldErrors()
                .stream().collect(Collectors.
                        toMap(FieldError::getField, FieldError::getDefaultMessage));
        ValidationError validationError = ValidationError.builder().
                errors(errors).
                code(HttpStatus.UNPROCESSABLE_ENTITY.value()).build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<?> handleAppException(final AppException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(exception.toResponse());
    }
}
