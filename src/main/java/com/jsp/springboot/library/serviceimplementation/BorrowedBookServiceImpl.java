package com.jsp.springboot.library.serviceimplementation;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;
import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.repository.BookRepository;
import com.jsp.springboot.library.repository.BorrowedBookRepository;
import com.jsp.springboot.library.repository.UserRepository;
import com.jsp.springboot.library.service.BorrowedBookService;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BorrowedBook borrowBook(Long userId, Long bookId, LocalDate dueDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUser(user);
        borrowedBook.setBook(book);
        borrowedBook.setDueDate(dueDate);
        borrowedBook.setIssueDate(LocalDate.now()); // Set current date as issue date
        borrowedBook.setReturnDate(null); // Initially null since book is not returned yet

        borrowedBookRepository.save(borrowedBook);

        // Update book status
        book.setStatus(BookStatus.ISSUED);
        bookRepository.save(book);

        return borrowedBook;
    }

 


    @Override
    public List<BorrowedBook> getUserBorrowedBooks(Long userId) {
        return borrowedBookRepository.findByUserId(userId);
    }

    @Override
    public void returnBook(Long borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new RuntimeException("Borrowed book record not found"));

        borrowedBook.setReturnDate(LocalDate.now()); // Set return date as today
        borrowedBookRepository.save(borrowedBook);

        // Update book status
        Book book = borrowedBook.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
    }

    @Override
    public Optional<BorrowedBook> getBorrowedBookById(Long id) {
        return borrowedBookRepository.findById(id);
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByBook(Book book) {
        return borrowedBookRepository.findByBook(book);
    }

    @Override
    public List<BorrowedBook> getOverdueBooks() {
        LocalDate today = LocalDate.now();
        return borrowedBookRepository.findByDueDateBefore(today);
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksByUser(User user) {
        return borrowedBookRepository.findByUser(user);
    }
}
