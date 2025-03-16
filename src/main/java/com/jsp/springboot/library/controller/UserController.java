package com.jsp.springboot.library.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jsp.springboot.library.entity.Role;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.service.UserService;
import com.jsp.springboot.library.utility.ResponseStructure;

@RestController
@RequestMapping("/api/users")  // Standardized base path
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Register a user
    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<User>> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Optional<User>>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ✅ Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseStructure<Optional<User>>> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    // ✅ Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseStructure<Optional<User>>> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // ✅ Get users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<ResponseStructure<List<User>>> getUsersByRole(@PathVariable String role) {
        Role parsedRole;
        try {
            parsedRole = Role.valueOf(role.toUpperCase()); // Convert string to Role Enum
        } catch (IllegalArgumentException e) {
            ResponseStructure<List<User>> response = new ResponseStructure<>();
            response.setStatuscode(400);
            response.setMessage("Invalid role specified.");
            response.setEntity(null);
            return ResponseEntity.badRequest().body(response);
        }
        return userService.getUsersByRole(parsedRole);
    }

    // ✅ Check if email exists
    @GetMapping("/email-exists/{email}")
    public ResponseEntity<ResponseStructure<Boolean>> isEmailExists(@PathVariable String email) {
        return userService.isEmailExists(email);
    }
}

   


