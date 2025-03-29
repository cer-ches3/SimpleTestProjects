package com.example.CaloriesTrackingService.controllers;

import com.example.CaloriesTrackingService.model.dto.MealDto;
import com.example.CaloriesTrackingService.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/meal")
@RequiredArgsConstructor
public class MealControllerV1 {
    private final MealService mealService;

    @GetMapping("/{id}")
    public ResponseEntity getMealById(@PathVariable long id) {
        return mealService.getById(id);
    }

    @GetMapping
    public Collection<MealDto> getAllMeals() {
        return mealService.getAll();
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity createNewMeal(@PathVariable long userId, @RequestBody MealDto mealDto) {
        return mealService.create(userId, mealDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMeal(@PathVariable long id, @RequestBody MealDto mealDto) {
        return mealService.update(id, mealDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMealById(@PathVariable long id) {
        return mealService.deleteById(id);
    }
}
