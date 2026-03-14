package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.CartDTO;
import com.vikrant.ecommerce.entity.CartItem;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Override
    public CartItem createItemForCart(CartDTO cartdto) {
        CartItem cartItem = new CartItem();
        if (cartdto.getQuantity() != null) {
            cartItem.setCartItemQuantity(cartdto.getQuantity());
        } else {
            cartItem.setCartItemQuantity(1); // Default quantity
        }
        return cartItem;
    }
}
