package com.example.studentsAccounting.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {

    @EventListener
    public void handleStudentAddedEvent(StudentAddedEvent event) {
        System.out.println("Студент добавлен: " + event.getStudent());
    }

    @EventListener
    public void handleStudentDeletedEvent(StudentDeletedEvent event) {
        System.out.println("Студент с ID " + event.getStudentId() + " удален.");
    }
}
