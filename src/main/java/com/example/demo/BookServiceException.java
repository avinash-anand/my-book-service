package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Exception occurred while processing this request")
public class BookServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public BookServiceException(String msg, Throwable e) {
        super(msg, e);
    }
    
    public BookServiceException(String msg) {
        super(msg);
    }
    
}
