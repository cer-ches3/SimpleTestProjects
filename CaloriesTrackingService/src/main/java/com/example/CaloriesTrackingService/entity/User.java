package com.example.CaloriesTrackingService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_list")
@Getter
@Setter
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
