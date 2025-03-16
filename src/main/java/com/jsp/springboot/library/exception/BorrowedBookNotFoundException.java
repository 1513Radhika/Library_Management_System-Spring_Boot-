package com.jsp.springboot.library.exception;

@SuppressWarnings("serial")
public class BorrowedBookNotFoundException extends RuntimeException {

    public BorrowedBookNotFoundException(String message) {
        super(message);
    }
}
