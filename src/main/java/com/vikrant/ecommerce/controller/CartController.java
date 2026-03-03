package com.vikrant.ecommerce.controller;

import com.vikrant.ecommerce.entity.Cart;
import com.vikrant.ecommerce.entity.CartDTO;
import com.vikrant.ecommerce.exception.CartItemNotFound;
import com.vikrant.ecommerce.exception.ProductNotFound;
import com.vikrant.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartDTO cart, 
                                                   @RequestHeader("token") String token) throws CartItemNotFound {
        Cart updatedCart = cartService.addProductToCart(cart, token);
        return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> getCartProducts(@RequestHeader("token") String token) {
        Cart cart = cartService.getCartProduct(token);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestBody CartDTO cart, 
                                                       @RequestHeader("token") String token) throws ProductNotFound {
        Cart updatedCart = cartService.removeProductFromCart(cart, token);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("token") String token) {
        Cart cart = cartService.clearCart(token);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
