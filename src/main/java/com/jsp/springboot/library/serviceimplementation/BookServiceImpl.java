package com.jsp.springboot.library.serviceimplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;
import com.jsp.springboot.library.exception.BookUnavailableException;
import com.jsp.springboot.library.repository.BookRepository;
import com.jsp.springboot.library.service.BookService;
import com.jsp.springboot.library.utility.ResponseStructure;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResponseEntity<ResponseStructure<Book>> addBook(Book book) {
        Book savedBook = bookRepository.save(book);
        ResponseStructure<Book> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.CREATED.value());
        responseStructure.setMessage("Book added successfully");
        responseStructure.setEntity(savedBook);

        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<Optional<Book>>> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookUnavailableException("Book not found with ID: " + id);
        }

        ResponseStructure<Optional<Book>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setMessage("Book found successfully");
        responseStructure.setEntity(book);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Optional<Book>>> getBookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isEmpty()) {
            throw new BookUnavailableException("Book not found with ISBN: " + isbn);
        }

        ResponseStructure<Optional<Book>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setMessage("Book found successfully");
        responseStructure.setEntity(book);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Book>>> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        if (books.isEmpty()) {
            throw new BookUnavailableException("No books found with title: " + title);
        }

        ResponseStructure<List<Book>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setMessage("Books found by title");
        responseStructure.setEntity(books);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Book>>> searchBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (books.isEmpty()) {
            throw new BookUnavailableException("No books found with author: " + author);
        }

        ResponseStructure<List<Book>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setMessage("Books found by author");
        responseStructure.setEntity(books);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Book>>> getBooksByStatus(BookStatus status) {
        List<Book> books = bookRepository.findByStatus(status);

        if (books.isEmpty()) {
            throw new BookUnavailableException("No books found with status: " + status);
        }

        ResponseStructure<List<Book>> response = new ResponseStructure<>();
        response.setMessage("Books retrieved successfully");
        response.setStatuscode(HttpStatus.OK.value());
        response.setEntity(books);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResponseStructure<Book>> updateBookStatus(Long bookId, BookStatus status) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookUnavailableException("Book not found with ID: " + bookId);
        }

        Book book = optionalBook.get();
        book.setStatus(status); // Directly assign the enum value

        Book updatedBook = bookRepository.save(book);

        ResponseStructure<Book> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setMessage("Book status updated successfully");
        responseStructure.setEntity(updatedBook);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

	


}
