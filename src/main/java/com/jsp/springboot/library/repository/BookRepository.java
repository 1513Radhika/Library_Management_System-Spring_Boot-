package com.jsp.springboot.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);  // Find book by ISBN

    List<Book> findByTitleContainingIgnoreCase(String title);  // Search books by title

    List<Book> findByAuthorContainingIgnoreCase(String author);  // Search books by author

    List<Book> findByStatus(BookStatus status);  // Find books by status (AVAILABLE, ISSUED, RESERVED)
}
