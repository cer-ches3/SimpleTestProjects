package org.example.socialNetworkUsers.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "social_network_id")
    private SocialNetwork socialNetwork;
}
