package com.example.authorbookrest.entity;

import com.example.authorbookrest.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String password;

    private String email;

    private String imagePath;

    @Enumerated(EnumType.STRING)
    private UserType userType;


}
