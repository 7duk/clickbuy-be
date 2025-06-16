package dev.sideproject.ndx2.mapper;

import dev.sideproject.ndx2.dto.response.ItemImageResponse;
import dev.sideproject.ndx2.entity.ItemImage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemImageMapper {
    ItemImageMapper INSTANCE = Mappers.getMapper(ItemImageMapper.class);
    ItemImageResponse toItemImageResponse(
            ItemImage image);
}
