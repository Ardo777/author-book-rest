package com.example.authorbookrest.service.impl;

import com.example.authorbookrest.dto.BookFilterDto;
import com.example.authorbookrest.dto.BookResponseDto;
import com.example.authorbookrest.dto.CBCurrencyResponseDto;
import com.example.authorbookrest.dto.SaveBookDto;
import com.example.authorbookrest.entity.Book;
import com.example.authorbookrest.entity.QBook;
import com.example.authorbookrest.mapper.BookMapper;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.repository.BookRepository;
import com.example.authorbookrest.service.BookService;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final RestTemplate restTemplate;

    private final EntityManager entityManager;

    @Override
    public BookResponseDto createBook(SaveBookDto saveBookDto) {
        Book book = bookMapper.map(saveBookDto);
        book.setAuthor(authorRepository.findById(saveBookDto.getAuthorId()).orElse(null));
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    @Override
    public List<BookResponseDto> getAll() {
        List<Book> all = bookRepository.findAll();
        List<BookResponseDto> bookDto = new ArrayList<>();
        if (!all.isEmpty()) {
            double usdCurrency = getUsdCurrency();
            for (Book book : all) {
                BookResponseDto bookResponseDto = bookMapper.map(book);
                setUsdPrice(bookResponseDto, usdCurrency);
                bookDto.add(bookResponseDto);
            }
        }
        return bookDto;
    }

    @Override
    public List<BookResponseDto> getAllByFilter(BookFilterDto bookFilterDto) {
        JPAQuery<Book> query = new JPAQuery(entityManager);
        QBook qBook = QBook.book;
        JPAQueryBase from = query.from(qBook);
        if (StringUtils.isNotBlank(bookFilterDto.getTitle())) {
            from.where(qBook.title.contains(bookFilterDto.getTitle()));
        }
        if (StringUtils.isNotBlank(bookFilterDto.getDescription())) {
            from.where(qBook.description.contains(bookFilterDto.getDescription()));
        }
        if (bookFilterDto.getMaxPrice() != null && bookFilterDto.getMinPrice() != null) {
            from.where(qBook.price.between(bookFilterDto.getMinPrice(), bookFilterDto.getMaxPrice()));
        } else if (bookFilterDto.getMinPrice() != null) {
            from.where(qBook.price.goe(bookFilterDto.getMinPrice()));
        } else if (bookFilterDto.getMaxPrice() != null) {
            from.where(qBook.price.loe(bookFilterDto.getMaxPrice()));
        }
        List<Book> fetch = query.fetch();
        List<BookResponseDto> bookDtos = new ArrayList<>();
        for (Book book : fetch) {
            bookDtos.add(bookMapper.map(book));
        }
        return bookDtos;
    }

    private void setUsdPrice(BookResponseDto bookResponseDto, double usdCurrency) {
        bookResponseDto.setPriceUSD(bookResponseDto.getPrice() / usdCurrency);
    }

    private double getUsdCurrency() {
        ResponseEntity<CBCurrencyResponseDto> forEntity = restTemplate.getForEntity("https://cb.am/latest.json.php", CBCurrencyResponseDto.class);
        if (forEntity.getStatusCode() == HttpStatusCode.valueOf(200)) {
            CBCurrencyResponseDto body = forEntity.getBody();
            return Double.parseDouble(body.getUsd());
        }
        return 0;
    }

}
