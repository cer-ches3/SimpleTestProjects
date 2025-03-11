package org.example.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class NewsApp {
    public static void main(String[] args) {
        ApplicationContext context =  SpringApplication.run(NewsApp.class, args);
    }
}