package dev.sideproject.ndx2.advice;

import dev.sideproject.ndx2.dto.ErrorResponse;
import dev.sideproject.ndx2.dto.ValidationError;
import dev.sideproject.ndx2.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        ValidationError validationError = new ValidationError();
        Map<String, String> errors = exception.getBindingResult().getFieldErrors()
                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        validationError.setErrors(errors);
        validationError.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        validationError.setTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(validationError);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<?> handleAppException(final AppException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getErrorCode().getMessage());
        errorResponse.setCode(exception.getErrorCode().getHttpStatus().value());
        errorResponse.setTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}
