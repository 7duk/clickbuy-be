package dev.sideproject.ndx2.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sideproject.ndx2.dto.ItemImageResponse;
import dev.sideproject.ndx2.dto.ItemResponse;
import dev.sideproject.ndx2.entity.Item;
import org.mapstruct.Mapper;

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
