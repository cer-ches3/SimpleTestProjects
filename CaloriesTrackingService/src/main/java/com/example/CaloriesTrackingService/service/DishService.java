package com.example.CaloriesTrackingService.service;

import com.example.CaloriesTrackingService.entity.Dish;
import com.example.CaloriesTrackingService.repositories.DishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishService implements CRUDService<Dish>{
    private final DishRepository dishRepository;

    @Override
    public ResponseEntity getById(long id) {
        Dish existsDish = dishRepository.findById(id).orElse(null);

        if (existsDish == null) {
            log.error("Dish with ID: {} not found!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("Dish with ID: {0} not found!", id));
        }

        log.info("Get dish with ID: {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(existsDish);
    }

    @Override
    public Collection<Dish> getAll() {
        log.info("Get all dishes.");
        return dishRepository.findAll();
    }

    @Override
    public ResponseEntity create(Dish dish) {
        if (dishRepository.existsDishByName(dish.getName())) {
            log.error("Dish with name {} already in use!", dish.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Dish with name {0} already in use!", dish.getName()));
        }
        if (!nameIsValid(dish.getName())) {
            log.error("Name: {} invalid! The name should consist of only letters!", dish.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Name: {0} invalid! The name should consist of only letters!", dish.getName()));
        }
        if (!countCaloriesIsValid(dish.getCountCaloriesPerServing())) {
            log.error("Count calories {} invalid!", dish.getCountCaloriesPerServing());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count calories {0} invalid! The count must not be less than 0.1.", dish.getCountCaloriesPerServing()));
        }
        if (!proteinsIsValid(dish.getProteins())) {
            log.error("Count proteins {} invalid!", dish.getProteins());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count proteins {0} invalid! The count must not be less than 0.1.", dish.getProteins()));
        }
        if (!fatsIsValid(dish.getFats())) {
            log.error("Count fats {} invalid!", dish.getFats());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count fats {0} invalid! The count must not be less than 0.1.", dish.getFats()));
        }
        if (!carbohydratesIsValid(dish.getCarbohydrates())) {
            log.error("Count carbohydrates {} invalid!", dish.getCarbohydrates());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count carbohydrates {0} invalid! The count must not be less than 0.1", dish.getCarbohydrates()));
        }

        Dish newDish = new Dish();
        newDish.setName(dish.getName());
        newDish.setCountCaloriesPerServing(dish.getCountCaloriesPerServing());
        newDish.setProteins(dish.getProteins());
        newDish.setFats(dish.getFats());
        newDish.setCarbohydrates(dish.getCarbohydrates());
        dishRepository.save(newDish);

        log.info("Created new dish: {}.", newDish);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDish);
    }

    @Override
    public ResponseEntity update(Dish dish) {
        Dish existsDish = dishRepository.findDishByName(dish.getName()).orElse(null);

        if (existsDish == null) {
            log.error("Dish with name {} not found!", dish.getName());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("Dish with name {0} not found!", dish.getName()));
        }
        if (!countCaloriesIsValid(dish.getCountCaloriesPerServing())) {
            log.error("Count calories {} invalid!", dish.getCountCaloriesPerServing());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count calories {0} invalid! The count must not be less than 0.1.", dish.getCountCaloriesPerServing()));
        }
        if (!proteinsIsValid(dish.getProteins())) {
            log.error("Count proteins {} invalid!", dish.getProteins());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count proteins {0} invalid! The count must not be less than 0.1.", dish.getProteins()));
        }
        if (!fatsIsValid(dish.getFats())) {
            log.error("Count fats {} invalid!", dish.getFats());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count fats {0} invalid! The count must not be less than 0.1.", dish.getFats()));
        }
        if (!carbohydratesIsValid(dish.getCarbohydrates())) {
            log.error("Count carbohydrates {} invalid!", dish.getCarbohydrates());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Count carbohydrates {0} invalid! The count must not be less than 0.1", dish.getCarbohydrates()));
        }

        existsDish.setCountCaloriesPerServing(dish.getCountCaloriesPerServing());
        existsDish.setProteins(dish.getProteins());
        existsDish.setFats(dish.getFats());
        existsDish.setCarbohydrates(dish.getCarbohydrates());
        dishRepository.save(existsDish);

        log.info("Update data dish with name: {}.", existsDish.getName());
        return ResponseEntity.status(HttpStatus.OK).body(existsDish);
    }

    @Override
    public ResponseEntity deleteById(long id) {
        Dish existsDish = dishRepository.findById(id).orElse(null);

        if (existsDish == null) {
            log.error("Dish with ID: {} not found!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("Dish with ID: {0} not found!", id));
        }
        dishRepository.delete(existsDish);

        log.info("Dish with ID: {} is deleted.", id);
        return ResponseEntity.status(HttpStatus.OK).body(MessageFormat.format("Dish with ID: {0} is deleted.", id));
    }

    private boolean nameIsValid(String name) {
        log.info("Call nameIsValid.");

        String regex = "[A-zА-яЁё]{2,}";

        return name.matches(regex);
    }

    private boolean countCaloriesIsValid(double countCalories) {
        log.info("Call countCaloriesIsValid.");

        return countCalories >= 0.1;
    }

    private boolean proteinsIsValid(double proteins) {
        log.info("Call proteinsIsValid.");

        return proteins >= 0.1;
    }

    private boolean fatsIsValid(double fats) {
        log.info("Call fatsIsValid.");

        return fats >= 0.1;
    }

    private boolean carbohydratesIsValid(double carbohydrates) {
        log.info("Call carbohydratesIsValid.");

        return carbohydrates >= 0.1;
    }
}
