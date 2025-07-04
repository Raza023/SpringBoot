package com.example.caching.business;

import com.example.caching.dto.UserDto;
import com.example.caching.entity.User;
import com.example.caching.repository.UserDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserBusinessTest {

    @InjectMocks
    private UserBusiness userBusiness;

    @Mock
    private UserDataService userDataService;

    private User user;
    private UserDto userDtoResult;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Hassan");
        user.setEmail("hassan@example.com");

        userDtoResult = new UserDto();
        userDtoResult.setId(1L);
        userDtoResult.setName("Hassan");
        userDtoResult.setEmail("hassan@example.com");
    }

    @Test
    void testGetAllUsers() {
        when(userDataService.findAll()).thenReturn(List.of(user));
        List<UserDto> result = userBusiness.getUsers();
        assertEquals(1, result.size());
    }

    @Test
    void testGetUserByIdFound() {
        when(userDataService.findById(1L)).thenReturn(Optional.of(user));
        UserDto result = userBusiness.getUserById(1L);
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userDataService.findById(1L)).thenReturn(Optional.empty());
        UserDto result = userBusiness.getUserById(1L);
        assertNull(result);
    }

    @Test
    void testGetUserByEmailFound() {
        when(userDataService.findByEmail("hassan@example.com")).thenReturn(Optional.of(user));
        UserDto result = userBusiness.getUserByEmail("hassan@example.com");
        assertNotNull(result);
        assertEquals("hassan@example.com", result.getEmail());
    }

    @Test
    void testGetUserByEmailNotFound() {
        when(userDataService.findByEmail("notfound@example.com")).thenReturn(Optional.empty());
        assertNull(userBusiness.getUserByEmail("notfound@example.com"));
    }

    @Test
    void testSaveUser() {
        when(userDataService.saveAndFlush(user)).thenReturn(user);
        UserDto result = userBusiness.saveUser(user);
        assertNotNull(result);
        assertEquals("hassan@example.com", result.getEmail());
    }

    @Test
    void testUpdateUserSuccess() {
        User updatedUser = new User();
        updatedUser.setName("Updated");
        updatedUser.setEmail("updated@example.com");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Updated");
        savedUser.setEmail("updated@example.com");

        when(userDataService.findById(1L)).thenReturn(Optional.of(user));
        when(userDataService.saveAndFlush(any(User.class))).thenReturn(savedUser);

        UserDto result = userBusiness.updateUser(1L, updatedUser);
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    void testUpdateUserNotFound() {
        when(userDataService.findById(1L)).thenReturn(Optional.empty());
        assertNull(userBusiness.updateUser(1L, user));
    }

    @Test
    void testDeleteUserSuccess() {
        when(userDataService.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userDataService).deleteById(1L);
        doNothing().when(userDataService).flush();
        int result = userBusiness.deleteUser(1L);
        assertEquals(1, result);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userDataService.findById(1L)).thenReturn(Optional.empty());
        int result = userBusiness.deleteUser(1L);
        assertEquals(0, result);
    }
}
