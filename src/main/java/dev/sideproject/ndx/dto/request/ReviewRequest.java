package dev.sideproject.ndx.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    @NotNull(message="Content field must be not null")
    String content;

    @JsonProperty("created_by")
    @NotNull(message="Created_by field must be not null")
    Integer lastModifiedBy;

    @NotNull(message="Rating field must be not null")
    Integer rating;
}
