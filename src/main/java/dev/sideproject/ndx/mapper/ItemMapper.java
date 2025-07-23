package dev.sideproject.ndx.mapper;

import dev.sideproject.ndx.dto.response.ItemResponse;
import dev.sideproject.ndx.entity.Item;

public interface ItemMapper {
    static ItemResponse mapToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .publicPrice(item.getPublicPrice())
                .discount(item.getDiscount())
                .amount(item.getAmount())
                .rating(item.getRating())
                .description(item.getDescription())
                .images(item.getImageItems().stream().map(ItemImageMapper.INSTANCE::toItemImageResponse).toList())
                .build();
    }
}
