package com.relatosPapel.books.service.impl;

import com.relatosPapel.books.domain.Book;
import com.relatosPapel.books.repository.BookRepository;
import com.relatosPapel.books.repository.DataAccessRepository;
import com.relatosPapel.books.service.BookService;
import com.relatosPapel.books.service.dto.BookDTO;
import com.relatosPapel.books.service.dto.BooksQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final DataAccessRepository repository;


    @Override
    public BooksQueryDTO getBooks(String title, String summary, String genre, String isbn,Integer rating, Boolean aggregate) {
        return repository.findBooks(title, summary, genre, isbn,rating, aggregate);
    }

    @Override
    public Book getBook(String bookId) {
        return repository.findById(bookId).orElse(null);
    }

    @Override
    public Boolean deleteBook(String bookId) {
        Book book = repository.findById(bookId).orElse(null);
        if (!ObjectUtils.isEmpty(book)) {
            repository.delete(book);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Book createBook(BookDTO request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .releaseDate(request.getReleaseDate())
                .genre(request.getGenre())
                .isbn(request.getIsbn())
                .rating(request.getRating())
                .visible(request.getVisible())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .build();
        return repository.save(book);
    }

    @Override
    public Book updateBook(String bookId, BookDTO bookDetails) {
        Book book = repository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setReleaseDate(bookDetails.getReleaseDate());
        book.setGenre(bookDetails.getGenre());
        book.setIsbn(bookDetails.getIsbn());
        book.setRating(bookDetails.getRating());
        book.setVisible(bookDetails.getVisible());
        book.setPrice(bookDetails.getPrice());
        book.setImageUrl(bookDetails.getImageUrl());
        return repository.save(book);
    }

    @Override
    public Book patchBook(String id, Book bookDetails) {
        Book book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        if (bookDetails.getTitle() != null) book.setTitle(bookDetails.getTitle());
        if (bookDetails.getAuthor() != null) book.setAuthor(bookDetails.getAuthor());
        if (bookDetails.getReleaseDate() != null) book.setReleaseDate(bookDetails.getReleaseDate());
        if (bookDetails.getGenre() != null) book.setGenre(bookDetails.getGenre());
        if (bookDetails.getIsbn() != null) book.setIsbn(bookDetails.getIsbn());
        if (bookDetails.getRating() != null) book.setRating(bookDetails.getRating());
        if (bookDetails.getVisible() != null) book.setVisible(bookDetails.getVisible());
        if (bookDetails.getPrice() != null) book.setPrice(bookDetails.getPrice());
        if (bookDetails.getImageUrl() != null) book.setImageUrl(bookDetails.getImageUrl());
        return repository.save(book);
    }

}
