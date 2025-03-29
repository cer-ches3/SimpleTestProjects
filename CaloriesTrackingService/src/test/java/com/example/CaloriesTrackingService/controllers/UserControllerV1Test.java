package com.example.CaloriesTrackingService.controllers;

import com.example.CaloriesTrackingService.model.entity.User;
import com.example.CaloriesTrackingService.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserControllerV1Test {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerV1 userControllerV1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getUserById")
    public void testGetUserById() {
        long id = 1L;
        when(userService.getById(id)).thenReturn(ResponseEntity.ok(new User()));

        ResponseEntity result = userControllerV1.getUserById(id);

        verify(userService, times(1)).getById(id);
    }

    @Test
    @DisplayName("Test getAllUser")
    public void testGetAllUser() {
        List<User> users = new ArrayList<>();

        when(userService.getAll()).thenReturn(users);

        List<User> result = (List<User>) userControllerV1.getAllUsers();

        verify(userService, times(1)).getAll();
    }

    @Test
    @DisplayName("Test createNewUser")
    public void testCreateNewUser() {
        User user = new User();

        when(userService.create(user)).thenReturn(ResponseEntity.ok(user));

        ResponseEntity result = userControllerV1.createNewUser(user);

        verify(userService, times(1)).create(user);
    }

    @Test
    @DisplayName("Test updateUser")
    public void testUpdateUser() {
        User user = new User();

        when(userService.update(user)).thenReturn(ResponseEntity.ok(user));

        ResponseEntity result = userControllerV1.updateUser(user);

        verify(userService, times(1)).update(user);
    }

    @Test
    @DisplayName("Test deleteUserById")
    public void testDeleteUserById() {
        long id = 1L;
        when(userService.deleteById(id)).thenReturn(ResponseEntity.ok(""));

        ResponseEntity result = userControllerV1.deleteUserById(id);

        verify(userService, times(1)).deleteById(id);
    }
}
