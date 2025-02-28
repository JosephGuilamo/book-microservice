package com.relatosPapel.books.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDate;
@Document(indexName = "books", createIndex = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book implements Serializable {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Text,name = "author")
    private String author;

    @Field(type = FieldType.Date, name = "releaseDate")
    private LocalDate releaseDate;

    @Field(type = FieldType.Keyword,name = "genre")
    private String genre;

    @Field(type = FieldType.Text,name = "isbn")
    private String isbn;

    @Field(type = FieldType.Integer,name = "rating")
    private Integer rating;

    @Field(type = FieldType.Boolean,name = "visible")
    private Boolean visible;

    @Field(type = FieldType.Double,name = "price")
    private Double price;

    @Field(type = FieldType.Text,name = "image_url")
    private String imageUrl;

    @Field(type = FieldType.Search_As_You_Type,name = "summary")
    private String summary;

    @Field(type = FieldType.Search_As_You_Type,name = "tags")
    private String tags;

}