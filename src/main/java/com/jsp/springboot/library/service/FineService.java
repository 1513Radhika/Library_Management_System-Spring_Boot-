package com.jsp.springboot.library.service;



import java.util.Optional;

import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.Fine;

public interface FineService {

    void generateFine(Long borrowedBookId, Double amount);  // Generate fine for a borrowed book

    Optional<Fine> getFineByBorrowedBook(BorrowedBook borrowedBook);  // Get fine for a borrowed book

    void payFine(Long fineId);  // Mark fine as paid
}

