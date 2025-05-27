package dev.sideproject.ndx2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.sideproject.ndx2.dto.AccountDto;
import dev.sideproject.ndx2.dto.SuccessResponse;
import dev.sideproject.ndx2.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthController {
    final RestTemplate restTemplate;
    final AccountService accountService;

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code,
                                      HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Thêm Basic Authentication
        String credentials = Base64.getEncoder()
                .encodeToString("client-id:client-secret".getBytes());
        headers.set("Authorization", "Basic " + credentials);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", "http://localhost:8080/oauth2/callback");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:8080/oauth2/token", requestEntity, Object.class);
        SuccessResponse successResponse = SuccessResponse.builder().
                message("Get Token Succeed!").
                code(HttpStatus.OK.value()).data(response.getBody()).build();
        return ResponseEntity.ok(successResponse);

    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody AccountDto accountDto) throws JsonProcessingException {
        AccountDto accountDtoCreated = accountService.register(accountDto);
        int code = HttpStatus.OK.value();
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Created successfully.").data(accountDtoCreated)
                .code(code).build();
        return ResponseEntity.status(code).body(successResponse);
    }
}
