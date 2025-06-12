package dev.sideproject.ndx2.controller;

import dev.sideproject.ndx2.dto.CartRequest;
import dev.sideproject.ndx2.security.CustomUserDetails;
import dev.sideproject.ndx2.service.CartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal=true)
public class CartController extends Controller {
    CartService cartService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<?> getItemsCart(Authentication auth){
        Integer accountId = ((CustomUserDetails)(auth.getPrincipal())).getId();
        return response(HttpStatus.OK,"get items in cart successfully",cartService.getItemInCart(accountId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> addOrUpdateItemInCart(Authentication auth,@Valid @RequestBody CartRequest cartRequest, @PathVariable("itemId") Integer itemId){
        Integer accountId = ((CustomUserDetails)(auth.getPrincipal())).getId();
        cartRequest.setAccountId(accountId);
        cartRequest.setItemId(itemId);
        return response(HttpStatus.OK,"added or updated item in cart",cartService.addOrUpdateItemInCart(cartRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        cartService.deleteItemIdCart(id);
        return response(HttpStatus.OK,"removed item in cart",null);
    }
}
