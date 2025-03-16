package com.jsp.springboot.library.service;

import org.springframework.http.ResponseEntity;

import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.Fine;
import com.jsp.springboot.library.utility.ResponseStructure;

public interface FineService {

    ResponseEntity<ResponseStructure<Fine>> generateFine(Long borrowedBookId, Double amount);



    ResponseEntity<ResponseStructure<String>> payFine(Long fineId);

	ResponseEntity<ResponseStructure<Fine>> getFineByBorrowedBook(BorrowedBook borrowedBook);



	ResponseEntity<ResponseStructure<Fine>> getFineByBorrowedBook(Long borrowedBookId);
}
