package com.example.CaloriesTrackingService.services;

import com.example.CaloriesTrackingService.model.entity.Meal;
import com.example.CaloriesTrackingService.repositories.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MealServiceTest {
    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getById if success")
    public void testGetById_ifSuccess() {
        long id = 1L;

        when(mealRepository.findById(id)).thenReturn(Optional.of(new Meal()));

        ResponseEntity result = mealService.getById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(mealRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test getById if dish not found")
    public void testGetById_ifDishNotFound() {
        long id = 1L;

        when(mealRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity result = mealService.getById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(mealRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Test deleteById if success")
    public void testDeleteById_ifSuccess() {
        long id = 1L;
        Meal meal = new Meal();
        meal.setId(id);

        when(mealRepository.findById(id)).thenReturn(Optional.of(meal));

        ResponseEntity result = mealService.deleteById(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(mealRepository, times(1)).findById(id);
        verify(mealRepository, times(1)).delete(meal);
    }

    @Test
    @DisplayName("Test deleteById if user not found")
    public void testDeleteById_ifUserNotFound() {
        long id = 1L;
        Meal meal = new Meal();
        meal.setId(id);

        when(mealRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity result = mealService.deleteById(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(mealRepository, times(1)).findById(id);
        verify(mealRepository, times(0)).delete(meal);
    }
}
