package com.jsp.springboot.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.springboot.library.entity.Role;
import com.jsp.springboot.library.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);  // Find user by username

    Optional<User> findByEmail(String email);  // Find user by email

    List<User> findByRole(Role role);  // Find users by role (ADMIN, LIBRARIAN, STUDENT)

    boolean existsByEmail(String email);  // Check if an email is already registered
}
