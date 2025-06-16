package dev.sideproject.ndx2.service;

import dev.sideproject.ndx2.dto.request.CartRequest;
import dev.sideproject.ndx2.dto.response.CartResponse;

import java.util.List;

public interface CartService {
    List<CartResponse> getItemInCart(Integer accountId);
    CartResponse addOrUpdateItemInCart(CartRequest cartRequest);
    void deleteItemIdCart(Integer id);
}
