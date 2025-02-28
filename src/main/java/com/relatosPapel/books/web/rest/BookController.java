package com.relatosPapel.books.web.rest;

import com.relatosPapel.books.service.BookService;
import com.relatosPapel.books.domain.Book;
import com.relatosPapel.books.service.dto.BookDTO;
import com.relatosPapel.books.service.dto.BooksQueryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/books")
@Tag(name = "Book Controller", description = "API para gestionar libros")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO book) {
        log.info("Request create book {}", book);
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.ok(savedBook);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        log.info("Request get book by id {}", id);
        Book book = bookService.getBook(id);
        return ObjectUtils.isEmpty(book) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(book);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id,@Valid @RequestBody BookDTO bookDetails) {
        log.info("Request update book {}", bookDetails);
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable String id, @RequestBody Book bookDetails) {
        log.info("Request patch book {}", bookDetails);
        Book updatedBook = bookService.patchBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        log.info("Request delete book by id {}", id);
        Boolean result = bookService.deleteBook(id);
        return Boolean.TRUE.equals(result) ? ResponseEntity.notFound().build() : ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<BooksQueryDTO> searchBooks(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer valoracion,
            @RequestParam(required = false, defaultValue = "false") Boolean aggregate) {

        BooksQueryDTO books = bookService.getBooks(titulo, autor, categoria, isbn, valoracion, aggregate);
        return ResponseEntity.ok(books);
    }
}
