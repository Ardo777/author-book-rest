package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.PagingResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/books")
public class BookEndpoint {

    private final BookService bookService;

    @PostMapping()
    public BookResponseDto createBook(@RequestBody SaveBookDto saveBookdto){
        if (saveBookdto.getTitle()==null){
            throw new IllegalArgumentException("Title can't be null");
        }
        return  bookService.createBook(saveBookdto);
    }

    @GetMapping
    public List<BookResponseDto> getAll(){
        return bookService.getAll();
    }
    @PostMapping("/filter")
    public List<BookResponseDto> getAllByFilter(@RequestBody BookFilterDto bookFilterDto){
        return bookService.getAllByFilter(bookFilterDto);
    }

}
