package com.jsp.springboot.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.Fine;
import com.jsp.springboot.library.service.BorrowedBookService;
import com.jsp.springboot.library.service.FineService;
import com.jsp.springboot.library.utility.ResponseStructure;

@RestController
@RequestMapping("/api/fines")
public class FineController {

    @Autowired
    private FineService fineService;

    @Autowired
    private BorrowedBookService borrowedBookService;

    @PostMapping("/generate")
    public ResponseEntity<ResponseStructure<Fine>> generateFine(@RequestParam Long borrowedBookId, @RequestParam Double amount) {
        return fineService.generateFine(borrowedBookId, amount);
    }

    @GetMapping("/borrowed-book/{borrowedBookId}")
    public ResponseEntity<ResponseStructure<Fine>> getFineByBorrowedBook(@PathVariable Long borrowedBookId) {
        return borrowedBookService.getBorrowedBookById(borrowedBookId)
                .getBody()
                .getEntity()
                .map(fineService::getFineByBorrowedBook)
                .orElseThrow(() -> new RuntimeException("Borrowed book not found"));
    }

    @PostMapping("/pay/{fineId}")
    public ResponseEntity<ResponseStructure<String>> payFine(@PathVariable Long fineId) {
        return fineService.payFine(fineId);
    }
}
