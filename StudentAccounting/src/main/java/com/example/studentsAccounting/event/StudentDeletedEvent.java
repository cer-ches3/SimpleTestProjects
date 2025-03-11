package com.example.studentsAccounting.event;

import org.springframework.context.ApplicationEvent;

public class StudentDeletedEvent extends ApplicationEvent {
    private final int studentId;

    public StudentDeletedEvent(Object source, int studentId) {
        super(source);
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }
}
