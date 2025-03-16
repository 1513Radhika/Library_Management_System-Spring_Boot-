package com.jsp.springboot.library.controller;

import java.time.LocalDate;
import java.util.List;

public class BorrowRequest {
    private Long userId;
    private List<Long> bookIds;  // ✅ Should be List<Long>
    private LocalDate dueDate;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
