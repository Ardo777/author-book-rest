package com.example.authorbookrest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveBookDto {


    private String description;

    private double price;

    private String title;

    private int authorId;

}
