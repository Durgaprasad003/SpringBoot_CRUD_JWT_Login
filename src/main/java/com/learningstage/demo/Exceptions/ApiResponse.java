package com.learningstage.demo.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;

}

//
//✅ With Generics (Your Code)
//ApiResponse<ProductDto>
//ApiResponse<List<ProductDto>>
//ApiResponse<String>
//ApiResponse<Void>
//
//👉 Same class → different data types   in controller