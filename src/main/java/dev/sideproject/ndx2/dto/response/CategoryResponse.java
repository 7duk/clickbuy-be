package dev.sideproject.ndx2.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    @JsonProperty("category_id")
    Integer id;
    @JsonProperty("category_name")
    String name;
}
