package com.example.CaloriesTrackingService.controllers;

import com.example.CaloriesTrackingService.model.entity.User;
import com.example.CaloriesTrackingService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserControllerV1 {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable long id) {
        return userService.getById(id);
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity createNewUser(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id) {
        return userService.deleteById(id);
    }
}
