package com.example.CaloriesTrackingService.services;

import com.example.CaloriesTrackingService.model.entity.Dish;
import com.example.CaloriesTrackingService.repositories.DishRepository;
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

public class DishServiceTest {
    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getById if success")
    public void testGetById_ifSuccess() {
        long id = 1L;

        when(dishRepository.findById(id)).thenReturn(Optional.of(new Dish()));

        ResponseEntity result = dishService.getById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(dishRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test getById if dish not found")
    public void testGetById_ifDishNotFound() {
        long id = 1L;

        when(dishRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity result = dishService.getById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(dishRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test getAll")
    public void testGetAll() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish());
        dishes.add(new Dish());

        when(dishRepository.findAll()).thenReturn(dishes);

        List<Dish> result = (List<Dish>) dishService.getAll();

        assertEquals(2, result.size());
        verify(dishRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test create if success")
    public void testCreate_ifSuccess() {
        Dish dish = new Dish(1L, "name", 100.0, 1.0, 1.0, 1.0);

        when(dishRepository.save(dish)).thenReturn(dish);

        ResponseEntity result = dishService.create(dish);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Test create if exists dish by name")
    public void testCreate_ifExistsDishByName() {
        Dish dish = new Dish(1L, "name", 100.0, 1.0, 1.0, 1.0);

        when(dishRepository.existsDishByName(dish.getName())).thenReturn(true);

        ResponseEntity result = dishService.create(dish);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Test update if success")
    public void testUpdate_ifSuccess() {
        Dish dish = new Dish(1L, "name", 100.0, 1.0, 1.0, 1.0);

        when(dishRepository.findDishByName(dish.getName())).thenReturn(Optional.of(dish));
        when(dishRepository.save(dish)).thenReturn(dish);

        ResponseEntity result = dishService.update(dish);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(dishRepository, times(1)).findDishByName(dish.getName());
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    @DisplayName("Test update if dish not found")
    public void testUpdate_ifDishNotFound() {
        Dish dish = new Dish(1L, "name", 100.0, 1.0, 1.0, 1.0);

        when(dishRepository.findDishByName(dish.getName())).thenReturn(Optional.empty());
        when(dishRepository.save(dish)).thenReturn(dish);

        ResponseEntity result = dishService.update(dish);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(dishRepository, times(1)).findDishByName(dish.getName());
        verify(dishRepository, times(0)).save(dish);
    }

    @Test
    @DisplayName("Test deleteById if success")
    public void testDeleteById_ifSuccess() {
        long id = 1L;
        Dish dish = new Dish();
        dish.setId(id);

        when(dishRepository.findById(id)).thenReturn(Optional.of(dish));

        ResponseEntity result = dishService.deleteById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(dishRepository, times(1)).findById(id);
        verify(dishRepository, times(1)).delete(dish);
    }

    @Test
    @DisplayName("Test deleteById if user not found")
    public void testDeleteById_ifUserNotFound() {
        long id = 1L;
        Dish dish = new Dish();
        dish.setId(id);

        when(dishRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity result = dishService.deleteById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(dishRepository, times(1)).findById(id);
        verify(dishRepository, times(0)).delete(dish);
    }
}
