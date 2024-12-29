package com.example.go_healthy_be.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(unique = true, nullable = false)
    private String email;
    private String username;

    private String password;

    private String name;

    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    @OneToMany(mappedBy = "user")
    private List<FoodConsumption> foodConsumptions;

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules;   


}