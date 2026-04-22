package com.learningstage.demo.Exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
//👉 You said:
//
//when client gives wrong input → BadRequest
//when product not found in DB → ProductNotFound