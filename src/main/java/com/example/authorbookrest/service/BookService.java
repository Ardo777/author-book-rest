package com.example.authorbookrest.service;


import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.entity.Book;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(SaveBookDto saveBookDto);

}
