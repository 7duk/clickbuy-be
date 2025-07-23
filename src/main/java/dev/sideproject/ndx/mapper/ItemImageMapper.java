package dev.sideproject.ndx.mapper;

import dev.sideproject.ndx.dto.response.ItemImageResponse;
import dev.sideproject.ndx.entity.ItemImage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemImageMapper {
    ItemImageMapper INSTANCE = Mappers.getMapper(ItemImageMapper.class);

    ItemImageResponse toItemImageResponse(
            ItemImage image);
}
