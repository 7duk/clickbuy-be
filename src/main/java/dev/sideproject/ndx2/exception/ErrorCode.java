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
    JTI_IS_INVALID("jti is invalid",HttpStatus.BAD_REQUEST),
    ACCOUNT_DOES_NOT_EXIST("account doesn't exist",HttpStatus.BAD_REQUEST),
    TOKEN_INVALID("token is invalid",HttpStatus.BAD_REQUEST),
    ACCOUNT_VERIFIED("account has already been verified", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("password is invalid",HttpStatus.BAD_REQUEST);
    String message;
    HttpStatus httpStatus;
}
