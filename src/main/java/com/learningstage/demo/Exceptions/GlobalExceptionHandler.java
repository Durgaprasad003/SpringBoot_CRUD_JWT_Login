package com.learningstage.demo.Exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<?> handleProductNotFound(ProductNotFoundException ex)
//    {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<?> handleBadRequest(BadRequestException ex) {
//
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidationException(
//            org.springframework.web.bind.MethodArgumentNotValidException ex) {
//
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage())
//        );
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }



    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleProductNotFound(
            ProductNotFoundException ex,
            HttpServletRequest request) {

        logger.error("Product not found: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(new ApiResponse<>(false,error,ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        logger.warn("Bad request: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(new ApiResponse<>(false,error, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(new ApiResponse<>(false,error,message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        logger.error("Unexpected error: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Something went wrong",
                request.getRequestURI()
        );

        return new ResponseEntity<>(
                new ApiResponse<>(false, error, "Internal server error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


}

//getbindidingresult will give success and error list  and from that fielderror will give only  errorlist and
//getfield willl give fieldname and getdefaultmessage will give dto valid messge



//
//        HTTP Response
//        │
//        ├── Status Code → 404
//        │
//        └── Body (ResponseEntity)
//                │
//              └── ApiResponse
//                    │
//                    ├── success = false
//                    ├── message = "Product not found"
//                    │
//                    └── data (ErrorResponse)
        //                  ├── timestamp
        //                  ├── status
        //                  ├── error
        //                  ├── message
        //                  └── path


//{
//        "success": false,
//        "data": {
//        "timestamp": "...",
//        "status": 404,
//        "error": "Not Found",
//        "message": "Product not found",
//        "path": "/product/10"
//        },
//        "message": "Product not found"
//        }