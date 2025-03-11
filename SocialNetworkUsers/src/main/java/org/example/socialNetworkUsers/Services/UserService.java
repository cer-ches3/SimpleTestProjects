package org.example.socialNetworkUsers.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socialNetworkUsers.Dto.Message;
import org.example.socialNetworkUsers.Dto.UserDto;
import org.example.socialNetworkUsers.Entity.SocialNetwork;
import org.example.socialNetworkUsers.Entity.User;
import org.example.socialNetworkUsers.Repositories.SocialNetworkRepository;
import org.example.socialNetworkUsers.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements CRUDService<UserDto>{
    private final UserRepository userRepository;
    private final SocialNetworkRepository socialNetworkRepository;
    private final Message userNotFound = new Message("Пользователь с таким id не найден!");

    @Override
    public ResponseEntity getAll() {
        log.info("Getting all users");
        Collection<User> usersList = userRepository.findAll();
        if (usersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity getById(Integer id) {
        log.info("Getting user with id {}", id);
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(userNotFound, HttpStatus.NOT_FOUND);
        }else {
            User user = userRepository.findById(id).orElseThrow();
            return new ResponseEntity<>(mapToDto(user), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity create(UserDto userDto) {
        log.info("Creating user");
        User user = mapToEntity(userDto);
        Integer socialNetworkID = userDto.getSocialNetworkID();
        SocialNetwork socialNetwork = socialNetworkRepository.findById(socialNetworkID).orElseThrow();
        user.setSocialNetwork(socialNetwork);
        userRepository.save(user);
        return new ResponseEntity<>(mapToDto(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity update(Integer id, UserDto userDto) {
        log.info("Updating user with id {}", id);
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(userNotFound, HttpStatus.NOT_FOUND);
        }else {
            User user = mapToEntity(userDto);
            Integer socialNetworkID = userDto.getSocialNetworkID();
            SocialNetwork socialNetwork = socialNetworkRepository.findById(socialNetworkID).orElseThrow();
            user.setSocialNetwork(socialNetwork);
            userRepository.save(user);
            return new ResponseEntity<>(mapToDto(user), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity deleteById(int id) {
        log.info("Deleting user with id {}", id);
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(userNotFound, HttpStatus.NOT_FOUND);
        }else {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity clear() {
        log.info("Clearing all users");
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setCreationDate(user.getCreationDate());
        userDto.setSocialNetworkID(SocialNetworkService.mapToDto(user.getSocialNetwork()).getId());
        return userDto;
    }

    public static User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAge(userDto.getAge());
        user.setCreationDate(userDto.getCreationDate());
        return user;
    }
}
