package org.example.socialNetworkUsers.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDateTime creationDate;
    private Integer socialNetworkID;
}
