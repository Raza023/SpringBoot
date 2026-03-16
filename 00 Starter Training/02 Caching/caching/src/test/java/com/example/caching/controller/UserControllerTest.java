package com.example.caching.controller;

import com.example.caching.business.UserBusiness;
import com.example.caching.config.ApiResponse;
import com.example.caching.dto.UserDto;
import com.example.caching.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserBusiness userBusiness;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setName("Hassan");
        user.setEmail("hassan@example.com");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Hassan");
        userDto.setEmail("hassan@example.com");
    }

    @Test
    void testGetAllUsers() {
        when(userBusiness.getUsers()).thenReturn(List.of(userDto));

        ResponseEntity<?> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(List.class, response.getBody());
    }

    @Test
    void testGetUserByIdFound() {
        when(userBusiness.getUserById(1L)).thenReturn(userDto);

        ResponseEntity<?> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userBusiness.getUserById(1L)).thenReturn(null);

        ResponseEntity<?> response = userController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(ApiResponse.class, response.getBody());
        assertTrue(((ApiResponse) response.getBody()).getMessage().contains("not found"));
    }

    @Test
    void testGetUserByEmailFound() {
        when(userBusiness.getUserByEmail("hassan@example.com")).thenReturn(userDto);

        ResponseEntity<?> response = userController.getUserByEmail("hassan@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testGetUserByEmailException() {
        when(userBusiness.getUserByEmail("notfound@example.com"))
                .thenThrow(new RuntimeException("User not found"));

        ResponseEntity<?> response = userController.getUserByEmail("notfound@example.com");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(((ApiResponse) response.getBody()).getMessage().contains("Failed to fetch user"));
    }

    @Test
    void testCreateUserSuccess() {
        when(userBusiness.saveUser(user)).thenReturn(userDto);

        ResponseEntity<?> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testCreateUserFailure() {
        when(userBusiness.saveUser(user)).thenThrow(new RuntimeException("Invalid"));

        ResponseEntity<?> response = userController.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((ApiResponse) response.getBody()).getMessage().contains("Failed to create user"));
    }

    @Test
    void testUpdateUserSuccess() {
        when(userBusiness.updateUser(eq(1L), any(User.class))).thenReturn(userDto);

        ResponseEntity<?> response = userController.updateUser(1L, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testUpdateUserFailure() {
        when(userBusiness.updateUser(eq(1L), any(User.class))).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<?> response = userController.updateUser(1L, user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((ApiResponse) response.getBody()).getMessage().contains("Failed to update user"));
    }

    @Test
    void testDeleteUserSuccess() {
        when(userBusiness.deleteUser(1L)).thenReturn(1);

        ResponseEntity<?> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((ApiResponse) response.getBody()).getMessage().contains("deleted successfully"));
    }

    @Test
    void testDeleteUserNotFound() {
        when(userBusiness.deleteUser(1L)).thenReturn(0);

        ResponseEntity<?> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((ApiResponse) response.getBody()).getMessage().contains("does not exist"));
    }

    @Test
    void testDeleteUserException() {
        when(userBusiness.deleteUser(1L)).thenThrow(new RuntimeException("DB error"));

        ResponseEntity<?> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(((ApiResponse) response.getBody()).getMessage().contains("Failed to delete user"));
    }
}
