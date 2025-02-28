package com.relatosPapel.books.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements Serializable {

    private String id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @NotEmpty
    private String genre;

    @NotNull
    @NotEmpty
    private String isbn;

    @NotNull
    @NotEmpty
    private Integer rating;

    @NotNull
    private Boolean visible;

    @NotNull
    @Min(1)
    private Double price;

    @NotNull
    @NotEmpty
    private String imageUrl;

    @NotNull
    @NotEmpty
    private String summary;

    private String tags;
}
