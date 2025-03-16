package com.jsp.springboot.library.serviceimplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.Fine;
import com.jsp.springboot.library.exception.BorrowedBookNotFoundException;
import com.jsp.springboot.library.exception.FineNotFoundException;
import com.jsp.springboot.library.repository.BorrowedBookRepository;
import com.jsp.springboot.library.repository.FineRepository;
import com.jsp.springboot.library.service.FineService;
import com.jsp.springboot.library.utility.ResponseStructure;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Override
    public ResponseEntity<ResponseStructure<Fine>> generateFine(Long borrowedBookId, Double amount) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new BorrowedBookNotFoundException("Borrowed book record not found with ID: " + borrowedBookId));

        Fine fine = new Fine();
        fine.setBorrowedBook(borrowedBook);
        fine.setAmount(amount);
        fine.setPaid(false); // Mark fine as unpaid initially
        fineRepository.save(fine);

        // Response structure
        ResponseStructure<Fine> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.CREATED.value());
        response.setMessage("Fine generated successfully.");
        response.setEntity(fine);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<Fine>> getFineByBorrowedBook(Long borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new BorrowedBookNotFoundException("Borrowed book record not found with ID: " + borrowedBookId));

        Fine fine = fineRepository.findByBorrowedBook(borrowedBook)
                .orElseThrow(() -> new FineNotFoundException("No fine record found for borrowed book ID: " + borrowedBookId));

        // Response structure
        ResponseStructure<Fine> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Fine details retrieved successfully.");
        response.setEntity(fine);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> payFine(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new FineNotFoundException("Fine record not found with ID: " + fineId));

        fine.setPaid(true);
        fineRepository.save(fine);

        // Response structure
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Fine paid successfully.");
        response.setEntity("Fine ID " + fineId + " has been marked as paid.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ResponseStructure<Fine>> getFineByBorrowedBook(BorrowedBook borrowedBook) {
        Optional<Fine> fineOptional = fineRepository.findByBorrowedBook(borrowedBook);

        if (fineOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fine record not found for this borrowed book");
        }

        Fine fine = fineOptional.get();

        ResponseStructure<Fine> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setMessage("Fine details retrieved successfully");
        responseStructure.setEntity(fine);

        return ResponseEntity.ok(responseStructure);
    }




}
