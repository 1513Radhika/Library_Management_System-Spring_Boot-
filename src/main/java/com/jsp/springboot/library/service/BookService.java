package com.jsp.springboot.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BookStatus;
import com.jsp.springboot.library.utility.ResponseStructure;

public interface BookService {

	 ResponseEntity<ResponseStructure<Book>> addBook(Book book);

	    ResponseEntity<ResponseStructure<Optional<Book>>> getBookById(Long id);

	    ResponseEntity<ResponseStructure<Optional<Book>>> getBookByIsbn(String isbn);

	    ResponseEntity<ResponseStructure<List<Book>>> searchBooksByTitle(String title);

	    ResponseEntity<ResponseStructure<List<Book>>> searchBooksByAuthor(String author);


		ResponseEntity<ResponseStructure<Book>> updateBookStatus(Long bookId, BookStatus status);


		ResponseEntity<ResponseStructure<List<Book>>> getBooksByStatus(BookStatus status);

		
}
