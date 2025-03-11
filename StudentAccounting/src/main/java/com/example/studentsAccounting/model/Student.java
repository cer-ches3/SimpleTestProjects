package com.example.studentsAccounting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return "id: " + id +
                ", Имя: '" + firstName + '\'' +
                ", Фамилия: '" + lastName + '\'' +
                ", Возраст: " + age;
    }
}
