package dev.sideproject.ndx.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse extends AccountResponse{
    @JsonProperty("access_token")
    String accessToken;

    @JsonIgnore
    String refreshToken;
}
