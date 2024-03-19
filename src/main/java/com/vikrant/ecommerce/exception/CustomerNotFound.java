package com.vikrant.ecommerce.exception;

public class CustomerNotFound extends RuntimeException{

    public CustomerNotFound(){
        super();
    }

    public CustomerNotFound(String message){
        super(message);
    }
}
