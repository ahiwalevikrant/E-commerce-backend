package com.vikrant.ecommerce.exception;

public class CartItemNotFound extends  RuntimeException{

    public  CartItemNotFound(){

    }
    public  CartItemNotFound(String message){
        super(message);
    }
}
