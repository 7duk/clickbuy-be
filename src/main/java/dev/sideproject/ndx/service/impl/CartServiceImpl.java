package dev.sideproject.ndx.service.impl;

import dev.sideproject.ndx.dto.request.CartRequest;
import dev.sideproject.ndx.dto.response.CartResponse;
import dev.sideproject.ndx.mapper.CartMapper;
import dev.sideproject.ndx.service.CartService;
import dev.sideproject.ndx.entity.Cart;
import dev.sideproject.ndx.entity.Item;
import dev.sideproject.ndx.exception.AppException;
import dev.sideproject.ndx.exception.ErrorCode;
import dev.sideproject.ndx.repository.CartRepository;
import dev.sideproject.ndx.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    ItemRepository itemRepository;

    @Override
    public List<CartResponse> getItemInCart(Integer accountId) {
        List<Cart> itemInCart = cartRepository.findByAccountId(accountId);
        if (!itemInCart.isEmpty()) {
            return itemInCart.stream().map(CartMapper.INSTANCE::toCartResponse).toList();
        }
        return Collections.emptyList();
    }

    @Transactional
    @Override
    public CartResponse addOrUpdateItemInCart(CartRequest cartRequest) {
        Cart cartMapped = CartMapper.INSTANCE.toCart(cartRequest);
        Item item = itemRepository.findById(cartRequest.getItemId()).orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        cartMapped.setItem(item);
        Optional<Cart> existed = cartRepository.findByAccountIdAndItem(cartRequest.getAccountId(), item);
        if (existed.isEmpty()) {
            return CartMapper.INSTANCE.toCartResponse(cartRepository.save(cartMapped));
        } else {
            Cart existingCart = existed.get();
            existingCart.setQuantity(existingCart.getQuantity() + cartRequest.getQuantity());
            return CartMapper.INSTANCE.toCartResponse(cartRepository.save(existingCart));
        }
    }

    @Transactional
    @Override
    public void deleteItemIdCart(Integer id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
        cartRepository.delete(cart);
    }


}
