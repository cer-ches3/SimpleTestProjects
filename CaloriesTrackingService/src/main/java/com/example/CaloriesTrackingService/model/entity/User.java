package com.example.CaloriesTrackingService.model.entity;

import com.example.CaloriesTrackingService.model.enums.Gender;
import com.example.CaloriesTrackingService.model.enums.Goal;
import com.example.CaloriesTrackingService.model.enums.LifeStyle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "weight")
    private int weight;

    @Column(name = "height")
    private int height;

    @Column(name = "goal")
    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "life_style")
    @Enumerated(EnumType.STRING)
    private LifeStyle lifeStyle;

    @Column(name = "daily_calories_intake")
    private double dailyCaloriesIntake;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", goal=" + goal +
                ", gender=" + gender +
                ", lifeStyle=" + lifeStyle +
                ", dailyCaloriesIntake=" + dailyCaloriesIntake +
                '}';
    }
}
