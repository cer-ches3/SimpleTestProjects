package com.example.CaloriesTrackingService.service;

import com.example.CaloriesTrackingService.model.entity.User;
import com.example.CaloriesTrackingService.model.enums.Gender;
import com.example.CaloriesTrackingService.model.enums.LifeStyle;
import com.example.CaloriesTrackingService.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements CRUDService<User> {
    private final UserRepository userRepository;

    @Override
    public ResponseEntity getById(long id) {
        User existsUser = userRepository.findById(id).orElse(null);

        if (existsUser == null) {
            log.error("User with ID: {} not found!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("User with ID: {0} not found!", id));
        }

        log.info("Get user by ID: {}.", id);
        return ResponseEntity.status(HttpStatus.OK).body(existsUser);
    }

    @Override
    public Collection<User> getAll() {
        log.info("Get list all users.");
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity create(User user) {
        if (!nameIsValid(user.getName())) {
            log.error("Name: {} invalid! The name should consist of only letters!", user.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Name: {0} invalid! The name should consist of only letters!", user.getName()));
        }
        if (userRepository.existsUserByEmail(user.getEmail())) {
            log.error("Email: {} already in use!", user.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Email: {0} already in use!", user.getEmail()));
        }
        if (!emailIsValid(user.getEmail())) {
            log.error("Email: {} invalid!", user.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Email: {0} invalid!", user.getEmail()));
        }
        if (!ageIsValid(user.getAge())) {
            log.error("Age {} invalid!", user.getAge());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Age {0} invalid! The age must not be less than 1.", user.getAge()));
        }
        if (!weightIsValid(user.getWeight())) {
            log.error("Weight {} invalid!", user.getWeight());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Weight {0} invalid! The weight must not be less than 1.", user.getWeight()));
        }
        if (!heightIsValid(user.getHeight())) {
            log.error("Height {} invalid!", user.getHeight());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Height {0} invalid! The height must not be less than 1.", user.getHeight()));
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setAge(user.getAge());
        newUser.setWeight(user.getWeight());
        newUser.setHeight(user.getHeight());
        newUser.setGender(user.getGender());
        newUser.setGoal(user.getGoal());
        newUser.setLifeStyle(user.getLifeStyle());
        newUser.setDailyCaloriesIntake(calculationDailyCaloriesIntake(user.getAge(), user.getHeight(), user.getWeight(),
                user.getGender(), user.getLifeStyle()));
        userRepository.save(newUser);

        log.info("Create new user {}", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @Override
    public ResponseEntity update(User user) {
        User existsUser = userRepository.findUserByEmail(user.getEmail()).orElse(null);

        if (existsUser == null) {
            log.error("User with email: {} not found!", user.getEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("User with email: {0} not found!", user.getEmail()));
        }
        if (!nameIsValid(user.getName())) {
            log.error("Name: {} invalid! The name should consist of only letters!", user.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Name: {0} invalid! The name should consist of only letters!", user.getName()));
        }
        if (!ageIsValid(user.getAge())) {
            log.error("Age {} invalid!", user.getAge());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Age {0} invalid! The age must not be less than 1.", user.getAge()));
        }
        if (!weightIsValid(user.getWeight())) {
            log.error("Weight {} invalid!", user.getWeight());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Weight {0} invalid! The weight must not be less than 1.", user.getWeight()));
        }
        if (!heightIsValid(user.getHeight())) {
            log.error("Height {} invalid!", user.getHeight());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageFormat.format("Height {0} invalid! The height must not be less than 1.", user.getHeight()));
        }

        existsUser.setName(user.getName());
        existsUser.setAge(user.getAge());
        existsUser.setWeight(user.getWeight());
        existsUser.setHeight(user.getHeight());
        existsUser.setGender(user.getGender());
        existsUser.setGoal(user.getGoal());
        existsUser.setLifeStyle(user.getLifeStyle());
        existsUser.setDailyCaloriesIntake(calculationDailyCaloriesIntake(user.getAge(), user.getHeight(), user.getWeight(),
                user.getGender(), user.getLifeStyle()));
        userRepository.save(existsUser);

        log.info("Update data user with email: {}.", existsUser.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(existsUser);
    }

    @Override
    public ResponseEntity deleteById(long id) {
        User existsUser = userRepository.findById(id).orElse(null);

        if (existsUser == null) {
            log.error("User with ID: {} not found!", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageFormat.format("User with ID: {0} not found!", id));
        }
        userRepository.delete(existsUser);

        log.info("User with ID: {} is deleted!", id);
        return ResponseEntity.status(HttpStatus.OK).body(MessageFormat.format("User with ID: {0} is deleted!", id));
    }

    private Double calculationDailyCaloriesIntake(int age, int height, double weight,
                                                  Gender gender, LifeStyle lifeStyle) {
        log.info("Call calculationDailyCaloriesIntake.");

        double dailyCaloriesIntake = 0;
        double activityIndex = definitionActivityIndex(lifeStyle);

        if (gender.toString().equals("MALE")) {
            dailyCaloriesIntake = (88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age)) * activityIndex;
        } else {
            dailyCaloriesIntake = (447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age)) * activityIndex;
        }

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
        DecimalFormat df = new DecimalFormat("#.#", symbols);
        String formattedValue = df.format(dailyCaloriesIntake).replace(',', '.');

        return Double.valueOf(formattedValue);
    }

    private double definitionActivityIndex(LifeStyle lifeStyle) {
        log.info("Call definitionActivityIndex.");

        double activityIndex = 0;

        switch (lifeStyle) {
            case SEDENTARY -> {
                activityIndex += 1.2;
            }
            case WORKOUT_ONE_THREE -> {
                activityIndex += 1.375;
            }
            case WORKOUT_THREE_FIVE -> {
                activityIndex += 1.55;
            }
            case WORKOUT_SIX_SEVEN -> {
                activityIndex += 1.725;
            }
            case SPORTSMEN -> {
                activityIndex += 1.9;
            }
        }

        return activityIndex;
    }

    private boolean nameIsValid(String name) {
        log.info("Call nameIsValid.");

        String regex = "[A-zА-яЁё]{2,}";

        return name.matches(regex);
    }

    private boolean emailIsValid(String email) {
        log.info("Call emailIsValid.");

        String regex = "[\\w\\d-.]{3,}@[\\w]{2,}.[\\w]{2,3}";

        return email.matches(regex);
    }

    private boolean ageIsValid(int age) {
        log.info("Call ageIsValid.");

        return age >= 1;
    }

    private boolean weightIsValid(double weight) {
        log.info("Call weightIsValid.");

        return weight >= 1;
    }

    private boolean heightIsValid(int height) {
        log.info("Call heightIsValid.");

        return height >= 1;
    }
}
