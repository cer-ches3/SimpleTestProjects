package com.example.CaloriesTrackingService.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals_list")
@Getter
@Setter
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "meal_time")
    private LocalTime mealTime;

    @Column(name = "meal_date")
    private LocalDate mealDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Dish> dishes = new ArrayList<>();

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", mealTime=" + mealTime +
                ", mealDate=" + mealDate +
                ", user=" + user +
                ", dishes=" + dishes +
                '}';
    }
}
