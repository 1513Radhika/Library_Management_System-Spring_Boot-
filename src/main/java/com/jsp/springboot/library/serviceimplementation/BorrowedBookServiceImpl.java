package com.jsp.springboot.library.serviceimplementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;
import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.exception.BookUnavailableException;
import com.jsp.springboot.library.exception.BorrowedBookNotFoundException;
import com.jsp.springboot.library.exception.UserNotFoundException;
import com.jsp.springboot.library.repository.BookRepository;
import com.jsp.springboot.library.repository.BorrowedBookRepository;
import com.jsp.springboot.library.repository.UserRepository;
import com.jsp.springboot.library.service.BorrowedBookService;
import com.jsp.springboot.library.utility.ResponseStructure;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> borrowBooks(Long userId, List<Long> bookIds, LocalDate dueDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<Book> books = bookRepository.findAllById(bookIds);

        if (books.size() != bookIds.size()) {
            throw new BookUnavailableException("Some books in the list are not found.");
        }

        List<BorrowedBook> borrowedBooks = books.stream().map(book -> {
            if (book.getStatus() != BookStatus.AVAILABLE) {
                throw new BookUnavailableException("Book with ID " + book.getId() + " is not available for borrowing.");
            }

            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setUser(user);
            borrowedBook.setBook(book);
            borrowedBook.setDueDate(dueDate);
            borrowedBook.setIssueDate(LocalDate.now());
            borrowedBook.setReturnDate(null);

            book.setStatus(BookStatus.ISSUED);
            return borrowedBook;
        }).collect(Collectors.toList());

        borrowedBookRepository.saveAll(borrowedBooks);
        bookRepository.saveAll(books);

        ResponseStructure<List<BorrowedBook>> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.CREATED.value());
        response.setMessage("Books borrowed successfully.");
        response.setEntity(borrowedBooks);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getUserBorrowedBooks(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUserId(userId);
        ResponseStructure<List<BorrowedBook>> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Borrowed books found for user.");
        response.setEntity(borrowedBooks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<String>> returnBook(Long borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new BorrowedBookNotFoundException("Borrowed book record not found with ID: " + borrowedBookId));

        borrowedBook.setReturnDate(LocalDate.now());
        borrowedBookRepository.save(borrowedBook);

        // Update book status
        Book book = borrowedBook.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Book returned successfully.");
        response.setEntity("Book with ID " + borrowedBookId + " has been returned.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Optional<BorrowedBook>>> getBorrowedBookById(Long id) {
        Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findById(id);
        if (borrowedBook.isEmpty()) {
            throw new BorrowedBookNotFoundException("Borrowed book not found with ID: " + id);
        }

        ResponseStructure<Optional<BorrowedBook>> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.FOUND.value());
        response.setMessage("Borrowed book found.");
        response.setEntity(borrowedBook);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getBorrowedBooksByBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookUnavailableException("Book not found with ID: " + bookId));

        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByBook(book);
        
        ResponseStructure<List<BorrowedBook>> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Borrowed books found for the book.");
        response.setEntity(borrowedBooks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getOverdueBooks() {
        LocalDate today = LocalDate.now();
        List<BorrowedBook> overdueBooks = borrowedBookRepository.findByDueDateBefore(today);

        ResponseStructure<List<BorrowedBook>> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Overdue books found.");
        response.setEntity(overdueBooks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<BorrowedBook>>> getBorrowedBooksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByUser(user);

        ResponseStructure<List<BorrowedBook>> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.OK.value());
        response.setMessage("Borrowed books found for user.");
        response.setEntity(borrowedBooks);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
