package org.example.socialNetworkUsers.Dto;

import lombok.Data;

import java.util.List;

@Data
public class SocialNetworkDto {
    private Integer id;
    private String name;
    private List<UserDto> userList;
}
