package com.relatosPapel.books.service.dto;

import com.relatosPapel.books.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BooksQueryDTO {

    private List<Book> books;
    private List<AggregationDetailsDTO> aggs;
}
