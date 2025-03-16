package com.jsp.springboot.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.springboot.library.entity.BorrowedBook;
import com.jsp.springboot.library.entity.Fine;

import java.util.Optional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    Optional<Fine> findByBorrowedBook(BorrowedBook borrowedBook);  // Find fine by borrowed book
}
