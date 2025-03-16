package com.jsp.springboot.library.utility;

public class ResponseStructure<T> {  // Ensure generic type <T> is defined
    private int statuscode;
    private String message;
    private T entity;  // This should store generic data

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
