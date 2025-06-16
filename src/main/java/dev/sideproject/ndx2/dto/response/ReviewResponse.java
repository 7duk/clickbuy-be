package dev.sideproject.ndx2.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class ReviewResponse {
    UUID id;
    String content;
    Integer rating;
    @JsonProperty("item_id")
    Integer itemId;
    LocalDateTime lastModifiedAt;
    @JsonProperty("last_modified_by")
    Integer lastModifiedBy;
    String fullname;

    // Custom getter for JSON serialization
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss 'GMT'XXX")
    @JsonGetter("last_modified_at")
    public ZonedDateTime getFormattedLastModifiedAt() {
        if (lastModifiedAt == null) return null;
        return lastModifiedAt.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
    }

    // Hidden field
    @JsonIgnore
    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }
}
