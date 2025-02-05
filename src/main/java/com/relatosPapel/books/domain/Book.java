package com.relatosPapel.books.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "autor")
    private String autor;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "valoracion")
    private Integer valoracion;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "image_url")
    private String imageUrl;

}