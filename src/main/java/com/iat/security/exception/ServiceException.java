package com.iat.security.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

   private String code;
    private HttpStatus httpStatus;

    public ServiceException(String code, String message, HttpStatus httpStatus){
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
    
}
