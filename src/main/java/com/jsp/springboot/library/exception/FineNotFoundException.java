package com.jsp.springboot.library.exception;

@SuppressWarnings("serial")
public class FineNotFoundException extends RuntimeException {
    public FineNotFoundException(String message) {
        super(message);
    }
}

