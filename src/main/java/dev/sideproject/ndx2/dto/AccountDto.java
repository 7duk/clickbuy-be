package dev.sideproject.ndx2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Serializable {
    @JsonProperty(value = "id",
            access = JsonProperty.Access.READ_ONLY)
    Long id;
    @JsonProperty(value = "username", required = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    String username;
    @JsonProperty(value = "password", required = true,
            access = JsonProperty.Access.WRITE_ONLY)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    String password;
    @JsonProperty(value = "fullname", required = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    String fullName;
    @JsonProperty(value = "email", required = true)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    String email;
    @JsonProperty(value = "created_at", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;
    @JsonProperty(value = "created_by", access = JsonProperty.Access.READ_ONLY)
    Long createdBy;
    @JsonProperty(value = "last_modified_at", access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lastModifiedAt;
    @JsonProperty(value = "last_modified_by", access = JsonProperty.Access.READ_ONLY)
    Long lastModifiedBy;

}
