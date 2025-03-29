package com.example.CaloriesTrackingService.service;

import com.example.CaloriesTrackingService.model.entity.Dish;
import com.example.CaloriesTrackingService.model.entity.Meal;
import com.example.CaloriesTrackingService.repositories.MealRepository;
import com.example.CaloriesTrackingService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final UserRepository userRepository;
    private final MealRepository mealRepository;

    public ResponseEntity getReportForToday(long userId, LocalDate date) {
        StringBuilder builder = new StringBuilder();
        builder.append("Отчет на ")
                .append(date)
                .append('\n')
                .append("Количество приёмов пищи за день: ")
                .append(getCountMealsForDay(userId, date))
                .append('\n')
                .append("Количество калорий на день: ")
                .append(getCountCaloriesForDay(userId, date));

        log.info("Get report for day.");
        return ResponseEntity.status(HttpStatus.OK).body(builder);
    }

    public ResponseEntity getDailyAllowanceIsFulfilled(long id, LocalDate date) {
        double dailyCaloriesIntake = userRepository.findById(id).get().getDailyCaloriesIntake();
        double countCaloriesForDay = getCountCaloriesForDay(id, date);
        String result = (countCaloriesForDay >= dailyCaloriesIntake) ? "уложились" : "не уложились";


        StringBuilder builder = new StringBuilder();
        builder.append("Отчет на ")
                .append(date)
                .append('\n')
                .append("Суточная норма калорий: ")
                .append(dailyCaloriesIntake)
                .append('\n')
                .append("Количество калорий за сутки: ")
                .append(countCaloriesForDay)
                .append('\n')
                .append("Вы ")
                .append(result)
                .append(" в свою дневную норму калорий.");

        log.info("Get report daily allowance is fulfilled.");
        return ResponseEntity.status(HttpStatus.OK).body(builder);
    }

    public ResponseEntity getHistoryByDays(long userId) {
        List<Meal> meals = mealRepository.findByUserId(userId);

        StringBuilder builder = new StringBuilder();
        for (Meal meal : meals) {
            builder.append("Дата ").append(meal.getMealDate()).append('\n')
                    .append("Название: ").append(meal.getTitle()).append('\n')
                    .append("Список блюд:\n");

            for (Dish dish : meal.getDishes()) {
                builder.append(dish.getName())
                        .append(", ")
                        .append("Калории - ")
                        .append(dish.getCountCaloriesPerServing())
                        .append(", ")
                        .append("Белки - ")
                        .append(dish.getProteins())
                        .append(", ")
                        .append("Жиры - ")
                        .append(dish.getFats())
                        .append(", ")
                        .append("Углеводы - ")
                        .append(dish.getCarbohydrates())
                        .append('\n');
            }
            builder.append('\n');
        }

        log.info("Get history by days.");
        return ResponseEntity.status(HttpStatus.OK).body(builder);
    }

    private int getCountMealsForDay(long userId, LocalDate date) {
        int countMealsForDay = mealRepository.getCountMealsForToday(userId, date);

        log.info("Get count meals for day.");
        return countMealsForDay;
    }

    private double getCountCaloriesForDay(long userId, LocalDate date) {
        List<Meal> meals = mealRepository.findByUserIdAndDate(userId, date);
        double countCaloriesForDay = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToDouble(Dish::getCountCaloriesPerServing)
                .sum();

        log.info("Get count calories for day");
        return countCaloriesForDay;
    }
}
