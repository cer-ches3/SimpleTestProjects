package com.example.studentsAccounting.services;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class InitializerStudents {
    private final StudentService studentService;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        studentService.addStudent("name1", "lastname1", 1);
        studentService.addStudent("name2", "lastname2", 2);
        studentService.addStudent("name3", "lastname3", 3);
        System.out.println("Добавлен список студентов!");
    }
}
