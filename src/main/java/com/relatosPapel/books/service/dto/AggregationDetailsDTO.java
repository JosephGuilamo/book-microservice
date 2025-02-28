package com.relatosPapel.books.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AggregationDetailsDTO {

    private String key;
    private Integer count;
    private String uri;
}
