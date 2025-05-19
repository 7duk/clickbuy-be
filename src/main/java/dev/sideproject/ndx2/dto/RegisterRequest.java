package dev.sideproject.ndx2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RegisterRequest implements Serializable {
    @NotBlank
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("password")
    private String password;

    @NotBlank
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;
}

