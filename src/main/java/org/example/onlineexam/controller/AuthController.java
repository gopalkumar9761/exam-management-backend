package org.example.onlineexam.controller;

import org.example.onlineexam.entity.User;
import org.example.onlineexam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // React ke liye CORS enable kiya hai
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    // Login API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().body("Error: Invalid password!");
            }
        }
        return ResponseEntity.badRequest().body("Error: User not found!");
    }
}