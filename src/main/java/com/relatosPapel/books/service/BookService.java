package com.relatosPapel.books.service;

import com.relatosPapel.books.domain.Book;
import com.relatosPapel.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }


    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitulo(bookDetails.getTitulo());
        book.setAutor(bookDetails.getAutor());
        book.setFechaPublicacion(bookDetails.getFechaPublicacion());
        book.setCategoria(bookDetails.getCategoria());
        book.setIsbn(bookDetails.getIsbn());
        book.setValoracion(bookDetails.getValoracion());
        book.setVisible(bookDetails.getVisible());
        book.setPrecio(bookDetails.getPrecio());
        book.setImageUrl(bookDetails.getImageUrl());
        return bookRepository.save(book);
    }


    public Book patchBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        if (bookDetails.getTitulo() != null) book.setTitulo(bookDetails.getTitulo());
        if (bookDetails.getAutor() != null) book.setAutor(bookDetails.getAutor());
        if (bookDetails.getFechaPublicacion() != null) book.setFechaPublicacion(bookDetails.getFechaPublicacion());
        if (bookDetails.getCategoria() != null) book.setCategoria(bookDetails.getCategoria());
        if (bookDetails.getIsbn() != null) book.setIsbn(bookDetails.getIsbn());
        if (bookDetails.getValoracion() != null) book.setValoracion(bookDetails.getValoracion());
        if (bookDetails.getVisible() != null) book.setVisible(bookDetails.getVisible());
        if (bookDetails.getPrecio() != null) book.setPrecio(bookDetails.getPrecio());
        if (bookDetails.getImageUrl() != null) book.setImageUrl(bookDetails.getImageUrl());
        return bookRepository.save(book);
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    public List<Book> searchBooks(String titulo, String autor, String categoria, String isbn, Integer valoracion, Boolean visible) {
        return bookRepository.findByFilters(titulo, autor, categoria, isbn, valoracion, visible);
    }
}