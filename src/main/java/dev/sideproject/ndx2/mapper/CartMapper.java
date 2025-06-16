package dev.sideproject.ndx2.mapper;

import dev.sideproject.ndx2.dto.request.CartRequest;
import dev.sideproject.ndx2.dto.response.CartResponse;
import dev.sideproject.ndx2.entity.Cart;
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
