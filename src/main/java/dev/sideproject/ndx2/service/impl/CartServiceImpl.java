package dev.sideproject.ndx2.service.impl;

import dev.sideproject.ndx2.dto.CartRequest;
import dev.sideproject.ndx2.dto.CartResponse;
import dev.sideproject.ndx2.entity.Cart;
import dev.sideproject.ndx2.entity.Item;
import dev.sideproject.ndx2.exception.AppException;
import dev.sideproject.ndx2.exception.ErrorCode;
import dev.sideproject.ndx2.mapper.CartMapper;
import dev.sideproject.ndx2.repository.CartRepository;
import dev.sideproject.ndx2.repository.ItemRepository;
import dev.sideproject.ndx2.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
        return Collections.EMPTY_LIST;
    }

    @Transactional
    @Override
    public CartResponse addOrUpdateItemInCart(CartRequest cartRequest) {
        Cart cartMapped = CartMapper.INSTANCE.toCart(cartRequest);
        Item item = itemRepository.findById(cartRequest.getItemId()).orElseThrow(() -> {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        });
        cartMapped.setItem(item);
        return CartMapper.INSTANCE.toCartResponse(cartRepository.save(cartMapped));
    }

    @Transactional
    @Override
    public void deleteItemIdCart(Integer id) {
        boolean exists = cartRepository.existsById(id);
        if (!exists) {
            throw new AppException(ErrorCode.ITEM_NOT_FOUND);
        }
        else{
            cartRepository.deleteById(id);
        }
    }


}
