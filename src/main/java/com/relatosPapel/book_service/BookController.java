package com.relatosPapel.book_service;

import com.relatosPapel.book_service.Book;
import com.relatosPapel.book_service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.patchBook(id, bookDetails);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer valoracion,
            @RequestParam(required = false) Boolean visible) {

        List<Book> books = bookService.searchBooks(titulo, autor, categoria, isbn, valoracion, visible);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}