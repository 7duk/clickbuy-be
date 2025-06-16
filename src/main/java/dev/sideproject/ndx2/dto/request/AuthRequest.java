package dev.sideproject.ndx2.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRequest {
    @JsonProperty("access_token")
    @NotNull(message="Access token must be not null")
    private String accessToken;
}
