package com.jsp.springboot.library.utility;

import java.util.List;

public class ErrorStructure {
    private int statusCode;
    private String message;
    private List<String> errors;

    public ErrorStructure(int statusCode, String message, List<String> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
