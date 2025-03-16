package com.jsp.springboot.library.utility;


import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.springboot.library.exception.BookUnavailableException;
import com.jsp.springboot.library.exception.BorrowedBookNotFoundException;
import com.jsp.springboot.library.exception.DuplicateResourceException;
import com.jsp.springboot.library.exception.FineNotFoundException;
import com.jsp.springboot.library.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorStructure> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorStructure error = new ErrorStructure(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.singletonList("Resource not found"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorStructure> handleDuplicateResource(DuplicateResourceException ex) {
        ErrorStructure error = new ErrorStructure(HttpStatus.CONFLICT.value(), ex.getMessage(), Collections.singletonList("Duplicate resource exists"));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<ErrorStructure> handleBookUnavailable(BookUnavailableException ex) {
        ErrorStructure error = new ErrorStructure(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.singletonList("Book is currently unavailable"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BorrowedBookNotFoundException.class)
    public ResponseEntity<ErrorStructure> handleOverdueBook(BorrowedBookNotFoundException ex) {
        ErrorStructure error = new ErrorStructure(HttpStatus.FORBIDDEN.value(), ex.getMessage(), Collections.singletonList("User has overdue books"));
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FineNotFoundException.class)
    public ResponseEntity<ErrorStructure> handleFineNotPaid(FineNotFoundException ex) {
        ErrorStructure error = new ErrorStructure(HttpStatus.PAYMENT_REQUIRED.value(), ex.getMessage(), Collections.singletonList("Fine must be paid before proceeding"));
        return new ResponseEntity<>(error, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStructure> handleGeneralException(Exception ex) {
        ErrorStructure error = new ErrorStructure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

