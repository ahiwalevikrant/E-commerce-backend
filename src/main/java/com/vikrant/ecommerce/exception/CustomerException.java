package com.vikrant.ecommerce.exception;

public class CustomerException extends RuntimeException{

    public CustomerException(){
        super();
    }

    public CustomerException(String message){
        super(message);
    }
}
