package dev.sideproject.ndx2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemImageResponse {
    @JsonProperty("image_id")
    Integer id;
    @JsonProperty("image_link")
    String link;
}
