package com.example.CaloriesTrackingService;

import com.example.CaloriesTrackingService.model.entity.Dish;
import com.example.CaloriesTrackingService.model.entity.Meal;
import com.example.CaloriesTrackingService.model.entity.User;
import com.example.CaloriesTrackingService.repositories.DishRepository;
import com.example.CaloriesTrackingService.repositories.MealRepository;
import com.example.CaloriesTrackingService.repositories.UserRepository;
import com.example.CaloriesTrackingService.services.DishService;
import com.example.CaloriesTrackingService.services.MealService;
import com.example.CaloriesTrackingService.services.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CaloriesTrackingServiceApplicationTests {
    @LocalServerPort
    private Integer port;

    private TestRestTemplate template = new TestRestTemplate();

    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private DishService dishService;

    @InjectMocks
    private MealService mealService;

    @BeforeAll
    public static void beforeAll() {
        container.start();
    }

    @AfterAll
    public static void afterAll() {
        container.stop();
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @DisplayName("Test get connection with database")
    public void testGetConnectionWithDatabase() {
        long userId = 1L;
        User user = new User();

        long dishId = 1L;
        Dish dish = new Dish();

        long mealId = 1L;
        Meal meal = new Meal();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(mealRepository.findById(mealId)).thenReturn(Optional.of(meal));

        ResponseEntity resultForUser = userService.getById(userId);
        ResponseEntity resultForDish = dishService.getById(userId);
        ResponseEntity resultForMeal = mealService.getById(userId);

        assertEquals(HttpStatus.OK, resultForUser.getStatusCode());
        assertEquals(HttpStatus.OK, resultForDish.getStatusCode());
        assertEquals(HttpStatus.OK, resultForMeal.getStatusCode());
        verify(userRepository, times(1)).findById(userId);
        verify(dishRepository, times(1)).findById(userId);
        verify(mealRepository, times(1)).findById(userId);
    }
}
