package com.example.CaloriesTrackingService.services;

import com.example.CaloriesTrackingService.model.entity.User;
import com.example.CaloriesTrackingService.model.enums.Gender;
import com.example.CaloriesTrackingService.model.enums.Goal;
import com.example.CaloriesTrackingService.model.enums.LifeStyle;
import com.example.CaloriesTrackingService.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getById if success")
    public void testGetById_ifSuccess() {
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        ResponseEntity result = userService.getById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test getById if user not found")
    public void testGetById_ifUserNotFound() {
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity result = userService.getById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test getAll")
    public void testGetAll() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = (List<User>) userService.getAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test create if success")
    public void testCreate_ifSuccess() {
        User user = new User(1L, "user", "some@mail.ru", 28, 78, 178,
                Goal.MAINTENANCE, Gender.MALE, LifeStyle.SEDENTARY, 1000.0);

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity result = userService.create(user);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Test create if exists user by email")
    public void testCreate_ifExistsUserByEmail() {
        User user = new User(1L, "user", "some@mail.ru", 28, 78, 178,
                Goal.MAINTENANCE, Gender.MALE, LifeStyle.SEDENTARY, 1000.0);

        when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(true);

        ResponseEntity result = userService.create(user);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Test update if success")
    public void testUpdate_ifSuccess() {
        User user = new User(1L, "user", "some@mail.ru", 28, 78, 178,
                Goal.MAINTENANCE, Gender.MALE, LifeStyle.SEDENTARY, 1000.0);

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(new User()));
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity result = userService.update(user);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userRepository, times(1)).findUserByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Test update if user not found")
    public void testUpdate_ifUserNotFound() {
        User user = new User(1L, "user", "some@mail.ru", 28, 78, 178,
                Goal.MAINTENANCE, Gender.MALE, LifeStyle.SEDENTARY, 1000.0);

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity result = userService.update(user);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userRepository, times(1)).findUserByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Test deleteById if success")
    public void testDeleteById_ifSuccess() {
        long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        ResponseEntity result = userService.deleteById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Test deleteById if user not found")
    public void testDeleteById_ifUserNotFound() {
        long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity result = userService.deleteById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).delete(user);
    }
}
