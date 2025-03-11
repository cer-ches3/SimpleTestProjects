package com.example.studentsAccounting.services;

import com.example.studentsAccounting.event.StudentAddedEvent;
import com.example.studentsAccounting.event.StudentDeletedEvent;
import com.example.studentsAccounting.model.Student;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Component
public class StudentService {
    TreeMap<Integer, Student> students = new TreeMap<>();

    private final ApplicationEventPublisher eventPublisher;

    public StudentService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public TreeMap<Integer, Student> getStudents() {
        return students;
    }

    public void addStudent(String firstName, String lastName, int age) {
        Student student = new Student();
        int id = students.isEmpty() ? 1 : (students.lastKey() + 1);
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAge(age);
        students.put(id, student);
        eventPublisher.publishEvent(new StudentAddedEvent(this,student));
    }

    public void deleteStudentById(int id) {
        if (!students.containsKey(id)) {
            System.out.println("Студента с id=" + id + "  не существует!");
        } else {
            students.remove(id);
            eventPublisher.publishEvent(new StudentDeletedEvent(this, id));
        }
    }

    public void clean() {
        if (students.isEmpty()) {
            System.out.println("Очистка невозможна, список пуст!");
        } else {
            students.clear();
            System.out.println("Список студентов очищен!");
        }
    }
}
