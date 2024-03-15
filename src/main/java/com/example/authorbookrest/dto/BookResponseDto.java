package com.example.authorbookrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponseDto {

    private int id;

    private String description;

    private double price;

    private String title;

    private AuthorResponseDto authorResponseDto;

}
