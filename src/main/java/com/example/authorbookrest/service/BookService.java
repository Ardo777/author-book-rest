package com.example.authorbookrest.service;


import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(SaveBookDto saveBookDto);

    List<BookResponseDto> getAll();

    List<BookResponseDto> getAllByFilter(BookFilterDto bookFilterDto);
}
