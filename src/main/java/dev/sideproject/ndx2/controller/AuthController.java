package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.dto.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RestController
@RequestMapping("/oauth2")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthController {
    final RestTemplate restTemplate;

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
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Get Token Succeed!");
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(response.getBody());
        return ResponseEntity.ok(successResponse);

    }
}
