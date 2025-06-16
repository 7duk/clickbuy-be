package dev.sideproject.ndx2.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {
    @JsonProperty("old_password")
    String oldPassword;
    @JsonProperty("new_password")
    String newPassword;
    String fullname;
}
