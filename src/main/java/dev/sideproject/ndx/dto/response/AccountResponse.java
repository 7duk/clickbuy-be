package dev.sideproject.ndx.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("created_by")
    private Integer createdBy;

    @JsonProperty("last_modified_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    @JsonProperty("last_modified_by")
    private Integer lastModifiedBy;
}

