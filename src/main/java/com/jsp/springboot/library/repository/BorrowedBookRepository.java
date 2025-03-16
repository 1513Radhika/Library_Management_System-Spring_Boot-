package com.jsp.springboot.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.springboot.library.entity.Book;
import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

    List<BorrowedBook> findByUser(User user);  // Find borrowed books by user

    List<BorrowedBook> findByBook(Book book);  // Find borrowed records by book

    List<BorrowedBook> findByDueDateBefore(LocalDate date);  // Find overdue books

	List<BorrowedBook> findByUserId(Long userId);
}

