package com.relatosPapel.books.service;

import com.relatosPapel.books.domain.Book;
import com.relatosPapel.books.service.dto.BookDTO;
import com.relatosPapel.books.service.dto.BooksQueryDTO;


public interface BookService {

    BooksQueryDTO getBooks(String title, String summary, String genre,String isbn,Integer rating, Boolean aggregate);

    Book getBook(String bookId);

    Boolean deleteBook(String bookId);

    Book createBook(BookDTO request);

    Book updateBook(String bookId, BookDTO request);

    Book patchBook(String id, Book bookDetails);
}