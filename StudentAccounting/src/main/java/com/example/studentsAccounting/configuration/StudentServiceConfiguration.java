package com.example.studentsAccounting.configuration;

import com.example.studentsAccounting.services.InitializerStudents;
import com.example.studentsAccounting.services.StudentService;
import com.example.studentsAccounting.services.StudentsManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(StudentsManager.class)
public class StudentServiceConfiguration {
    @Bean
    public InitializerStudents initializerStudents(StudentService studentService) {
        return new InitializerStudents(studentService);
    }
}
