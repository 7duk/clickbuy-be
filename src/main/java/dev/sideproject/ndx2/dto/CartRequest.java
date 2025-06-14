package dev.sideproject.ndx2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    Integer id;

    @JsonIgnore
    Integer accountId;

    @JsonIgnore
    Integer itemId;

    @NotNull
    @Min(value = 0, message = "Quantity must be greater than 0")
    Integer quantity;
}
