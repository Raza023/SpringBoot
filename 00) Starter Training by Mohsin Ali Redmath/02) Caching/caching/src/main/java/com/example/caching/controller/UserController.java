package com.example.caching.controller;

import com.example.caching.business.UserBusiness;
import com.example.caching.config.ApiResponse;
import com.example.caching.dto.UserDto;
import com.example.caching.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/caching")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    // Get all users
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDto> users = userBusiness.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to fetch users: " + e.getMessage()));
        }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userBusiness.getUserById(id);
            if (Objects.isNull(userDto)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("User with id " + id + " not found."));
            }
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to fetch user: " + e.getMessage()));
        }
    }

    // Get user by email
    @GetMapping("/by-email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try {
            UserDto userDto = userBusiness.getUserByEmail(email);
            if (userDto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("User with email " + email + " not found."));
            }
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to fetch user: " + e.getMessage()));
        }
    }

    // Create user
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            UserDto savedUser = userBusiness.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to create user: " + e.getMessage()));
        }
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            UserDto updated = userBusiness.updateUser(id, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to update user: " + e.getMessage()));
        }
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            int result  = userBusiness.deleteUser(id);
            if(result == 0) {
                return ResponseEntity.ok(new ApiResponse("User not deleted as it does not exist."));
            }
            return ResponseEntity.ok(new ApiResponse("User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to delete user: " + e.getMessage()));
        }
    }
}
