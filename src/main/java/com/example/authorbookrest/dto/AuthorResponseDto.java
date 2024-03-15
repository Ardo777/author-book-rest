package com.example.authorbookrest.dto;


import com.example.authorbookrest.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponseDto {

    private int id;

    private  String name;

    private String surname;

    private Gender gender;

    private int age;

}
