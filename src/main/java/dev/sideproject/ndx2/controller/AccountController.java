package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.dto.request.AccountRequest;
import dev.sideproject.ndx2.dto.response.AccountResponse;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.security.CustomUserDetails;
import dev.sideproject.ndx2.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController extends Controller {
    AccountService accountService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam(name = "token") String token) {
        accountService.verify(token);
        return response(HttpStatus.OK, "your account is being verified, please check your mail", null);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/info/{id}")
    public ResponseEntity<?> info(@PathVariable(name = "id") Integer id) {
        AccountResponse accountResponse = accountService.details(id);
        return response(HttpStatus.OK, "account details retrieved", accountResponse);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(Authentication auth, @PathVariable("id") Integer accountId, @RequestBody AccountRequest accountRequest) {
        Integer id = ((CustomUserDetails) (auth.getPrincipal())).getId();
        if (accountId != id) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        accountService.changePassword(accountId, accountRequest);
        return response(HttpStatus.OK, "changed password", null);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PutMapping("/{id}/edit-profile")
    public ResponseEntity<?> editProfile(Authentication auth, @PathVariable("id") Integer accountId, @RequestBody AccountRequest accountRequest) {
        Integer id = ((CustomUserDetails) (auth.getPrincipal())).getId();
        if (accountId != id) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        accountService.editProfile(accountId, accountRequest);
        return response(HttpStatus.OK, "edited profile", null);
    }
}
