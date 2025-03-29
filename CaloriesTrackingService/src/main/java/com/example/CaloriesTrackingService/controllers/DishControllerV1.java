package com.example.CaloriesTrackingService.controllers;

import com.example.CaloriesTrackingService.model.entity.Dish;
import com.example.CaloriesTrackingService.services.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishControllerV1 {
    private final DishService dishService;

    @GetMapping("/{id}")
    public ResponseEntity getDishById(@PathVariable long id) {
        return dishService.getById(id);
    }

    @GetMapping
    public Collection<Dish> getAllDishes() {
        return dishService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity createNewDish(@RequestBody Dish dish) {
        return dishService.create(dish);
    }

    @PutMapping("/update")
    public ResponseEntity updateDish(@RequestBody Dish dish) {
        return dishService.update(dish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDishById(@PathVariable long id) {
        return dishService.deleteById(id);
    }

    @PostMapping("/init")
    public ResponseEntity initDishes() {
        return dishService.initDishes("src/main/resources/other/tablica_produktov.csv");
    }
}
