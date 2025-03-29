package com.example.CaloriesTrackingService.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MealDto {
    private long id;
    private String title;
    private LocalTime mealTime;
    private LocalDate mealDate;
    private long userId;
    private List<Long> dishes = new ArrayList<>();
}
