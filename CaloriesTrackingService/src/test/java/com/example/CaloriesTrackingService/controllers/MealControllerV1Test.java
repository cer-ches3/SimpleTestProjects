package com.example.CaloriesTrackingService.controllers;

import com.example.CaloriesTrackingService.model.entity.Dish;
import com.example.CaloriesTrackingService.services.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

public class MealControllerV1Test {
    @Mock
    private MealService mealService;

    @InjectMocks
    private MealControllerV1 mealControllerV1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getMealById")
    public void testGetMealById() {
        long id = 1L;
        when(mealService.getById(id)).thenReturn(ResponseEntity.ok(new Dish()));

        ResponseEntity result = mealControllerV1.getMealById(id);

        verify(mealService, times(1)).getById(id);
    }

    @Test
    @DisplayName("Test deleteDishById")
    public void testDeleteDishById() {
        long id = 1L;
        when(mealService.deleteById(id)).thenReturn(ResponseEntity.ok(""));

        ResponseEntity result = mealControllerV1.deleteMealById(id);

        verify(mealService, times(1)).deleteById(id);
    }
}
