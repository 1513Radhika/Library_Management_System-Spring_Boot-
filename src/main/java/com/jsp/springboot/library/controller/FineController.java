package com.jsp.springboot.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.Fine;
import com.jsp.springboot.library.service.BorrowedBookService;
import com.jsp.springboot.library.service.FineService;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;
    @Autowired
    private BorrowedBookService borrowedBookService; 

    @PostMapping("/generate")
    public ResponseEntity<String> generateFine(@RequestParam Long borrowedBookId, @RequestParam Double amount) {
        fineService.generateFine(borrowedBookId, amount);
        return ResponseEntity.ok("Fine generated successfully.");
    }


    @GetMapping("/borrowed-book/{borrowedBookId}")
    public ResponseEntity<Optional<Fine>> getFineByBorrowedBook(@PathVariable Long borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookService.getBorrowedBookById(borrowedBookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrowed book not found"));
        return ResponseEntity.ok(fineService.getFineByBorrowedBook(borrowedBook));
    }


    @PostMapping("/pay/{fineId}")
    public ResponseEntity<Void> payFine(@PathVariable Long fineId) {
        fineService.payFine(fineId);
        return ResponseEntity.noContent().build();
    }
}

