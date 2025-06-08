package dev.sideproject.ndx2.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponse {
    @JsonProperty("id")
    Integer id;
    @JsonProperty("item_name")
    String name;
    @JsonProperty("sell_price")
    Double sellPrice;
    @JsonProperty("buy_price")
    Double buyPrice;
    @JsonProperty("public_price")
    Double publicPrice;
    @JsonProperty("discount")
    Integer discount;
    @JsonProperty("description")
    String description;
    @JsonProperty("amount")
    Integer amount;
    @JsonProperty("rating")
    Double rating;
    @JsonProperty("category")
    CategoryResponse categoryResponse;
    @JsonProperty("images")
    List<ItemImageResponse> images;
}
