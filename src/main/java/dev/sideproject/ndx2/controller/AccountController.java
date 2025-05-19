package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.dto.SuccessResponse;
import dev.sideproject.ndx2.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam(name = "token") String token) {
        accountService.verify(token);
        int code = HttpStatus.OK.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .code(code).message("your account is being verified, please check your mail").build();
        return ResponseEntity.status(code).body(successResponse);
    }
}
