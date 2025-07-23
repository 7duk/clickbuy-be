package dev.sideproject.ndx.mapper;

import dev.sideproject.ndx.dto.request.CartRequest;
import dev.sideproject.ndx.dto.response.CartResponse;
import dev.sideproject.ndx.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses={ItemMapper.class})
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "item", expression = "java(ItemMapper.mapToItemResponse(cart.getItem()))")
    CartResponse toCartResponse(Cart cart);

    Cart toCart(CartRequest cartRequest);
}
