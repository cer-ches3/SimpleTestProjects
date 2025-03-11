package com.example.MyBookShopApp.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {
    private Integer id;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
