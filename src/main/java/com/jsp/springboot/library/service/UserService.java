package com.jsp.springboot.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import com.jsp.springboot.library.entity.User;
import com.jsp.springboot.library.entity.Role;
import com.jsp.springboot.library.utility.ResponseStructure;

public interface UserService {

    ResponseEntity<ResponseStructure<User>> registerUser(User user);

    ResponseEntity<ResponseStructure<Optional<User>>> getUserById(Long id);

    ResponseEntity<ResponseStructure<Optional<User>>> getUserByUsername(String username);

    ResponseEntity<ResponseStructure<Optional<User>>> getUserByEmail(String email);

    ResponseEntity<ResponseStructure<List<User>>> getUsersByRole(Role role);

    ResponseEntity<ResponseStructure<Boolean>> isEmailExists(String email);
}
