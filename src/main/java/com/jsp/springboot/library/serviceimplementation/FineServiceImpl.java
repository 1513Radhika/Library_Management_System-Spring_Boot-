package com.jsp.springboot.library.serviceimplementation;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.Fine;
import com.jsp.springboot.library.repository.FineRepository;
import com.jsp.springboot.library.repository.BorrowedBookRepository;
import com.jsp.springboot.library.service.FineService;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Override
    public void generateFine(Long borrowedBookId, Double amount) {
        Optional<BorrowedBook> borrowedBookOptional = borrowedBookRepository.findById(borrowedBookId);
        
        if (borrowedBookOptional.isPresent()) {
            BorrowedBook borrowedBook = borrowedBookOptional.get();

            Fine fine = new Fine();
            fine.setBorrowedBook(borrowedBook);
            fine.setAmount(amount);
            fine.setPaid(false); // Mark fine as unpaid initially
            
            fineRepository.save(fine);
        } else {
            throw new RuntimeException("Borrowed book record not found.");
        }
    }

    @Override
    public Optional<Fine> getFineByBorrowedBook(BorrowedBook borrowedBook) {
        return fineRepository.findByBorrowedBook(borrowedBook);
    }

    @Override
    public void payFine(Long fineId) {
        Optional<Fine> fineOptional = fineRepository.findById(fineId);
        
        if (fineOptional.isPresent()) {
            Fine fine = fineOptional.get();
            fine.setPaid(true); // Mark fine as paid
            fineRepository.save(fine);
        } else {
            throw new RuntimeException("Fine record not found.");
        }
    }
}
