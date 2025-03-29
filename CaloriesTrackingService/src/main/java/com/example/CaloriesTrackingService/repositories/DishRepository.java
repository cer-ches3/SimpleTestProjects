package com.example.CaloriesTrackingService.repositories;

import com.example.CaloriesTrackingService.model.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findDishByName(String name);
    boolean existsDishByName(String name);
}
