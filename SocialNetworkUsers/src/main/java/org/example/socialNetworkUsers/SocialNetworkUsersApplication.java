package org.example.socialNetworkUsers;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialNetworkUsersApplication
{
    public static long timeWorkingApplication = 0;
    public static long timeTimeStartingApplication = 0;


    public static void main(String[] args) {
        timeTimeStartingApplication = System.currentTimeMillis();
        SpringApplication.run(SocialNetworkUsersApplication.class, args);
        System.out.println("Приложение запущено и готово к работе.");

    }

    @PostConstruct
    public static void loadingApplication() {
        System.out.println("Выполняется запуск приложения.");
    }

    @PreDestroy
    public static void stopApplication() {
        System.out.println("Завершение работы приложения.");
        timeWorkingApplication = System.currentTimeMillis() - timeTimeStartingApplication;
        System.out.println("Время работы приложения: " + timeWorkingApplication / 1000 + " секунд.");

    }
}
