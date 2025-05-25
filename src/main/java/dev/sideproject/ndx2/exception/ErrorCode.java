package dev.sideproject.ndx2.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    USER_NOT_EXISTED("user not existed", HttpStatus.BAD_REQUEST),
    UN_AUTHORIZED("access denied", HttpStatus.FORBIDDEN),
    UN_AUTHENTICATED("unauthorized", HttpStatus.UNAUTHORIZED),
    REGISTER_FAILED("register failed", HttpStatus.INTERNAL_SERVER_ERROR),
    JTI_IS_NULL("jti must be not null",HttpStatus.INTERNAL_SERVER_ERROR);

    String message;
    HttpStatus httpStatus;
}
