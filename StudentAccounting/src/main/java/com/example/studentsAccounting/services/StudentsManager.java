package com.example.studentsAccounting.services;

import com.example.studentsAccounting.model.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.TreeMap;

@ShellComponent
@ConditionalOnProperty("app.initialization-at-startup.enabled")
public class StudentsManager {

    private final StudentService studentService;

    public StudentsManager(StudentService studentService) {
        this.studentService = studentService;
    }

    @ShellMethod(key = "show")
    public void getListStudents() {
        TreeMap<Integer, Student> students = studentService.getStudents();
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст!");
        } else {
            students.forEach((integer, student) ->
                    System.out.println(student));
        }
    }

    @ShellMethod(key = "add")
    public void addStudent(@ShellOption(value = "fn") String firstName,
                           @ShellOption(value = "ln") String lastName,
                           @ShellOption(value = "a") int age) {
        studentService.addStudent(firstName, lastName, age);
    }

    @ShellMethod(key = "delete")
    public void deleteStudentById(int id) {
        studentService.deleteStudentById(id);
    }

    @ShellMethod(key = "clean")
    public void clean() {
        studentService.clean();
    }
}
