package com.example.authorbookrest.repository;

import com.example.authorbookrest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
