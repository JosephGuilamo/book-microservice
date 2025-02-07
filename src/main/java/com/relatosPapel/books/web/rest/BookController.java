package com.relatosPapel.books.web.rest;

import com.relatosPapel.books.service.BookService;
import com.relatosPapel.books.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Controller", description = "API para gestionar libros")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Crear un nuevo libro", responses = {
            @ApiResponse(responseCode = "201", description = "Libro creado correctamente",
                    content = @Content(schema = @Schema(implementation = Book.class)))
    })
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un libro por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@Parameter(description = "ID del libro a buscar") @PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Actualizar un libro", responses = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = Book.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar parcialmente un libro", responses = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado parcialmente",
                    content = @Content(schema = @Schema(implementation = Book.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.patchBook(id, bookDetails);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un libro", responses = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar libros con filtros", responses = {
            @ApiResponse(responseCode = "200", description = "Libros encontrados",
                    content = @Content(schema = @Schema(implementation = Book.class)))
    })
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
