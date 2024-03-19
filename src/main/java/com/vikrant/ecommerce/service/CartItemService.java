package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.CartDTO;
import com.vikrant.ecommerce.entity.CartItem;

public interface CartItemService {

    public CartItem createItemForCart(CartDTO cartdto);


}
