package com.relatosPapel.book_service;

import com.relatosPapel.book_service.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
