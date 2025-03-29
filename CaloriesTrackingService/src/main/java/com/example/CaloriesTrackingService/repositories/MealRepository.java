package com.example.CaloriesTrackingService.repositories;

import com.example.CaloriesTrackingService.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    @Query(value = "select count(*) from meals_list t where t.user_id = :user_id and t.meal_date = :meal_date", nativeQuery = true)
    int getCountMealsForToday(@Param("user_id") long userId, @Param("meal_date") LocalDate date);

    @Query(value = "select * from meals_list t where t.user_id = :user_id and t.meal_date = :meal_date", nativeQuery = true)
    List<Meal> findByUserIdAndDate(@Param("user_id") long userId, @Param("meal_date") LocalDate date);

    @Query(value = "select * from meals_list t where t.user_id = :user_id", nativeQuery = true)
    List<Meal> findByUserId(@Param("user_id") long userId);
}
