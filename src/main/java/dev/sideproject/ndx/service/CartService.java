package dev.sideproject.ndx.service;

import dev.sideproject.ndx.dto.request.CartRequest;
import dev.sideproject.ndx.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getItemInCart(Integer accountId);

    CartResponse addOrUpdateItemInCart(CartRequest cartRequest);

    void deleteItemIdCart(Integer id);
}
