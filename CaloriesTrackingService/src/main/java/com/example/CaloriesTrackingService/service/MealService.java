package com.example.CaloriesTrackingService.service;

import com.example.CaloriesTrackingService.model.dto.MealDto;
import com.example.CaloriesTrackingService.model.entity.Dish;
import com.example.CaloriesTrackingService.model.entity.Meal;
import com.example.CaloriesTrackingService.repositories.DishRepository;
import com.example.CaloriesTrackingService.repositories.MealRepository;
import com.example.CaloriesTrackingService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public ResponseEntity getById(long id) {
        Meal existsMeal = mealRepository.findById(id).orElse(null);

        if (existsMeal == null) {
            log.error("Meal with ID: {} not found!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("Meal with ID: {0} not found!", id));
        }

        log.info("Get meal with ID: {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(existsMeal);
    }

    public Collection<MealDto> getAll() {
        log.info("Get all meals.");
        return mealRepository.findAll().stream()
                .map(MealService::mapToDto)
                .toList();
    }

    public ResponseEntity create(long userId, MealDto mealDto) {
        if (!userRepository.existsById(userId)) {
            log.error("User with ID: {} not found!", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("User with ID: {0} not found!", userId));
        }

        Meal meal = mapToEntity(mealDto);
        meal.setUser(userRepository.findById(userId).orElseThrow());
        meal.setDishes(mealDto.getDishes().stream()
                .map(id -> {
                    return dishRepository.findById(id).orElseThrow();
                })
                .collect(Collectors.toList())
        );
        mealRepository.save(meal);

        log.info("Create new meal {}", meal);
        return ResponseEntity.status(HttpStatus.CREATED).body(meal);
    }

    public ResponseEntity update(long id, MealDto mealDto) {
        Meal existsMeal = mealRepository.findById(id).orElse(null);

        if (existsMeal == null) {
            log.error("Meal with ID: {} not found!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("Meal with ID: {0} not found!", id));
        }

        Meal updateMeal = mapToEntity(mealDto);
        updateMeal.setId(existsMeal.getId());
        updateMeal.setUser(userRepository.findById(mealDto.getUserId()).orElseThrow());
        updateMeal.setDishes(mealDto.getDishes().stream()
                .map(dishId -> {
                    return dishRepository.findById(dishId).orElseThrow();
                })
                .collect(Collectors.toList())
        );
        mealRepository.save(updateMeal);

        log.info("Update meal with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(updateMeal);
    }

    public ResponseEntity deleteById(long id) {
        Meal existsMeal = mealRepository.findById(id).orElse(null);

        if (existsMeal == null) {
            log.error("Meal with ID: {} not found!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("Meal with ID: {0} not found!", id));
        }

        mealRepository.delete(existsMeal);

        log.info("Delete meal with ID: {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(MessageFormat.format("Meal with ID: {0} is deleted.", id));
    }

    private static MealDto mapToDto(Meal meal) {
        log.info("Call mapToDto.");

        MealDto mealDto = new MealDto();
        mealDto.setId(meal.getId());
        mealDto.setTitle(meal.getTitle());
        mealDto.setMealTime(meal.getMealTime());
        mealDto.setMealDate(meal.getMealDate());
        mealDto.setUserId(meal.getUser().getId());
        mealDto.setDishes(meal.getDishes().stream()
                .map(Dish::getId)
                .collect(Collectors.toList()));

        return mealDto;
    }

    private static Meal mapToEntity(MealDto mealDto) {
        log.info("Call mapToEntity.");

        Meal meal = new Meal();
        meal.setId(mealDto.getId());
        meal.setTitle(mealDto.getTitle());
        meal.setMealTime(mealDto.getMealTime());
        meal.setMealDate(mealDto.getMealDate());

        return meal;
    }
}
