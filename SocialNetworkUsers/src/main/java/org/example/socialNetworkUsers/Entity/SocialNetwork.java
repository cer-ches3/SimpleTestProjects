package org.example.socialNetworkUsers.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "social_networks")
@Data
public class SocialNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 30)
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "socialNetwork", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> usersList;
}
