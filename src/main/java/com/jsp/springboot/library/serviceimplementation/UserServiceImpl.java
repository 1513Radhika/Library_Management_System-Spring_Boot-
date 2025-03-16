package com.jsp.springboot.library.serviceimplementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.exception.UserNotFoundException;
import com.jsp.springboot.library.entity.Role;
import com.jsp.springboot.library.repository.UserRepository;
import com.jsp.springboot.library.service.UserService;
import com.jsp.springboot.library.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseStructure<User>> registerUser(User user) {
        User savedUser = userRepository.save(user);

        ResponseStructure<User> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.CREATED.value());
        responseStructure.setMessage("User registered successfully");
        responseStructure.setEntity(savedUser);

        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<Optional<User>>> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }

        ResponseStructure<Optional<User>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.FOUND.value());
        responseStructure.setMessage("User found successfully");
        responseStructure.setEntity(user);

        return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ResponseStructure<Optional<User>>> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with username '" + username + "' not found");
        }

        ResponseStructure<Optional<User>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.FOUND.value());
        responseStructure.setMessage("User found successfully");
        responseStructure.setEntity(user);

        return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ResponseStructure<Optional<User>>> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with email '" + email + "' not found");
        }

        ResponseStructure<Optional<User>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.FOUND.value());
        responseStructure.setMessage("User found successfully");
        responseStructure.setEntity(user);

        return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<User>>> getUsersByRole(Role role) {
        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found with role: " + role);
        }

        ResponseStructure<List<User>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.FOUND.value());
        responseStructure.setMessage("Users found successfully");
        responseStructure.setEntity(users);

        return new ResponseEntity<>(responseStructure, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ResponseStructure<Boolean>> isEmailExists(String email) {
        boolean exists = userRepository.existsByEmail(email);

        ResponseStructure<Boolean> responseStructure = new ResponseStructure<>();
        responseStructure.setStatuscode(HttpStatus.OK.value());
        responseStructure.setMessage("Email existence check completed");
        responseStructure.setEntity(exists);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }
}
