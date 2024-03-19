package com.vikrant.ecommerce.exception;

public class ProductNotFound extends RuntimeException{

    public ProductNotFound(){

    }
    public ProductNotFound(String message){
        super(message);
    }
}
