package com.relatosPapel.book_service;

import com.relatosPapel.book_service.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByTituloContainingAndAutorContainingAndCategoriaContainingAndIsbnContainingAndValoracionAndVisible(
            String titulo, String autor, String categoria, String isbn, Integer valoracion, Boolean visible);
}