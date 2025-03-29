package com.example.CaloriesTrackingService.controllers;

import com.example.CaloriesTrackingService.model.entity.Dish;
import com.example.CaloriesTrackingService.services.DishService;
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

public class DishControllerV1Test {
    @Mock
    private DishService dishService;

    @InjectMocks
    private DishControllerV1 dishControllerV1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getDishById")
    public void testGetDishById() {
        long id = 1L;
        when(dishService.getById(id)).thenReturn(ResponseEntity.ok(new Dish()));

        ResponseEntity result = dishControllerV1.getDishById(id);

        verify(dishService, times(1)).getById(id);
    }

    @Test
    @DisplayName("Test getAllDishes")
    public void testGetAllDishes() {
        List<Dish> dishes = new ArrayList<>();

        when(dishService.getAll()).thenReturn(dishes);

        List<Dish> result = (List<Dish>) dishControllerV1.getAllDishes();

        verify(dishService, times(1)).getAll();
    }

    @Test
    @DisplayName("Test createNewDish")
    public void testCreateNewDish() {
        Dish dish = new Dish();

        when(dishService.create(dish)).thenReturn(ResponseEntity.ok(dish));

        ResponseEntity result = dishControllerV1.createNewDish(dish);

        verify(dishService, times(1)).create(dish);
    }

    @Test
    @DisplayName("Test updateDish")
    public void testUpdateDish() {
        Dish dish = new Dish();

        when(dishService.update(dish)).thenReturn(ResponseEntity.ok(dish));

        ResponseEntity result = dishControllerV1.updateDish(dish);

        verify(dishService, times(1)).update(dish);
    }

    @Test
    @DisplayName("Test deleteDishById")
    public void testDeleteDishById() {
        long id = 1L;
        when(dishService.deleteById(id)).thenReturn(ResponseEntity.ok(""));

        ResponseEntity result = dishControllerV1.deleteDishById(id);

        verify(dishService, times(1)).deleteById(id);
    }
}
