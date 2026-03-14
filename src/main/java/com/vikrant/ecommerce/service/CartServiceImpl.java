package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.*;
import com.vikrant.ecommerce.exception.CartItemNotFound;
import com.vikrant.ecommerce.exception.CustomerNotFound;
import com.vikrant.ecommerce.exception.ProductNotFound;
import com.vikrant.ecommerce.repository.CartRepo;
import com.vikrant.ecommerce.repository.CustomerRepo;
import com.vikrant.ecommerce.repository.ProductRepo;
import com.vikrant.ecommerce.repository.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;
    
    @Autowired
    private ProductRepo productRepo;
    
    @Autowired
    private SessionRepo sessionRepo;
    
    @Autowired
    private CustomerRepo customerRepo;
    
    @Autowired
    private CartItemService cartItemService;

    private Customer getCustomerFromToken(String token) throws CustomerNotFound {
        UserSession session = sessionRepo.findByToken(token)
            .orElseThrow(() -> new CustomerNotFound("Invalid session token"));
        return customerRepo.findById(session.getUserId())
            .orElseThrow(() -> new CustomerNotFound("Customer not found"));
    }

    @Override
    public Cart addProductToCart(CartDTO cartDto, String token) throws CartItemNotFound {
        try {
            Customer customer = getCustomerFromToken(token);
            
            Cart cart = customer.getCustomercart();
            if (cart == null) {
                cart = new Cart();
                cart.setCustomer(customer);
                cart.setCartItems(new ArrayList<>());
                cart.setCartTotal(0.0);
            }

            Product product = productRepo.findById(cartDto.getProductId())
                .orElseThrow(() -> new ProductNotFound("Product not found"));

            CartItem newItem = cartItemService.createItemForCart(cartDto);
            newItem.setCartProduct(product);

            cart.getCartItems().add(newItem);
            
            // Update cart total
            double productPrice = product.getPrice() * newItem.getCartItemQuantity();
            cart.setCartTotal(cart.getCartTotal() + productPrice);

            return cartRepo.save(cart);
        } catch (CustomerNotFound e) {
            throw new CartItemNotFound(e.getMessage());
        }
    }

    @Override
    public Cart getCartProduct(String token) {
        try {
            Customer customer = getCustomerFromToken(token);
            Cart cart = customer.getCustomercart();
            if (cart == null) {
                cart = new Cart();
                cart.setCartItems(new ArrayList<>());
                cart.setCartTotal(0.0);
            }
            return cart;
        } catch (CustomerNotFound e) {
            throw new CartItemNotFound(e.getMessage());
        }
    }

    @Override
    public Cart removeProductFromCart(CartDTO cartDto, String token) throws ProductNotFound {
        try {
            Customer customer = getCustomerFromToken(token);
            Cart cart = customer.getCustomercart();
            
            if (cart == null || cart.getCartItems() == null) {
                throw new CartItemNotFound("Cart is empty");
            }

            Product product = productRepo.findById(cartDto.getProductId())
                .orElseThrow(() -> new ProductNotFound("Product not found"));

            cart.getCartItems().removeIf(item -> 
                item.getCartProduct() != null && 
                item.getCartProduct().getProductId().equals(product.getProductId()));

            // Recalculate cart total
            double newTotal = cart.getCartItems().stream()
                .mapToDouble(item -> item.getCartProduct().getPrice() * item.getCartItemQuantity())
                .sum();
            cart.setCartTotal(newTotal);

            return cartRepo.save(cart);
        } catch (CustomerNotFound e) {
            throw new CartItemNotFound(e.getMessage());
        }
    }

    @Override
    public Cart clearCart(String token) {
        try {
            Customer customer = getCustomerFromToken(token);
            Cart cart = customer.getCustomercart();
            
            if (cart != null) {
                cart.getCartItems().clear();
                cart.setCartTotal(0.0);
                return cartRepo.save(cart);
            }
            
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setCartItems(new ArrayList<>());
            newCart.setCartTotal(0.0);
            return cartRepo.save(newCart);
        } catch (CustomerNotFound e) {
            throw new CartItemNotFound(e.getMessage());
        }
    }
}
