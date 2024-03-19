package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.Cart;
import com.vikrant.ecommerce.entity.CartDTO;
import com.vikrant.ecommerce.exception.CartItemNotFound;
import com.vikrant.ecommerce.exception.ProductNotFound;

public interface CartService {
    public Cart addProductToCart(CartDTO cart, String token) throws CartItemNotFound;
    public Cart getCartProduct(String token);
    public Cart removeProductFromCart(CartDTO cartDto,String token) throws ProductNotFound;


    public Cart clearCart(String token);

}
