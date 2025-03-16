package com.jsp.springboot.library.service;



import java.util.List;
import java.util.Optional;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;

public interface BookService {

    Book addBook(Book book);  // Add a new book

    Optional<Book> getBookById(Long id);  // Get book by ID

    Optional<Book> getBookByIsbn(String isbn);  // Get book by ISBN

    List<Book> searchBooksByTitle(String title);  // Search books by title

    List<Book> searchBooksByAuthor(String author);  // Search books by author

    List<Book> getBooksByStatus(BookStatus status);  // Get books by status

    void updateBookStatus(Long bookId, BookStatus status);  // Update book status
}

