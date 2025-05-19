package dev.sideproject.ndx2.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE ,makeFinal = true)
public enum ErrorCode {
    USER_NOT_EXISTED("User not existed",HttpStatus.BAD_REQUEST),
    UN_AUTHORIZED("Access Denied",HttpStatus.FORBIDDEN),
    UN_AUTHENTICATED("Unauthorized",HttpStatus.UNAUTHORIZED);

    String message;
    HttpStatus httpStatus;
}
