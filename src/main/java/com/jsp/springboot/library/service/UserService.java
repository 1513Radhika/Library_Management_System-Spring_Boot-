package com.jsp.springboot.library.service;

import java.util.List;
import java.util.Optional;

import com.jsp.springboot.library.entity.Role;
import com.jsp.springboot.library.entity.User;

public interface UserService {

    User registerUser(User user);  // Register a new user

    Optional<User> getUserById(Long id);  // Get user by ID

    Optional<User> getUserByUsername(String username);  // Get user by username

    Optional<User> getUserByEmail(String email);  // Get user by email

    List<User> getUsersByRole(Role role);  // Get users by role

    boolean isEmailExists(String email);  // Check if email exists
}
