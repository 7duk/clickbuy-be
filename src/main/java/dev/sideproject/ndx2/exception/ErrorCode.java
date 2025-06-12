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
    USER_NOT_EXISTED("user not existed", HttpStatus.NOT_FOUND),
    UN_AUTHORIZED("access denied", HttpStatus.FORBIDDEN),
    UN_AUTHENTICATED("unauthorized", HttpStatus.UNAUTHORIZED),
    REGISTER_FAILED("register failed", HttpStatus.INTERNAL_SERVER_ERROR),
    JTI_IS_INVALID("jti is invalid",HttpStatus.BAD_REQUEST),
    ACCOUNT_DOES_NOT_EXIST("account doesn't exist",HttpStatus.NOT_FOUND),
    TOKEN_INVALID("token is invalid",HttpStatus.UNAUTHORIZED),
    ACCOUNT_VERIFIED("account has already been verified", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("password is invalid",HttpStatus.BAD_REQUEST),
    NOT_FOUND("not found", HttpStatus.NOT_FOUND),
    SAVE_ERROR("save operation failed", HttpStatus.INTERNAL_SERVER_ERROR),
    ITEM_NOT_FOUND("item not found", HttpStatus.NOT_FOUND);
    String message;
    HttpStatus httpStatus;
}
