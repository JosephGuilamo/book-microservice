package com.relatosPapel.books.repository;

import com.relatosPapel.books.domain.Book;
import com.relatosPapel.books.service.dto.AggregationDetailsDTO;
import com.relatosPapel.books.service.dto.BooksQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataAccessRepository {

    @Value("${server.fullAddress}")
    private String serverFullAddress;

    // Esta clase (y bean) es la unica que usan directamente los servicios para
    // acceder a los datos.
    private final BookRepository bookRepository;
    private final ElasticsearchOperations elasticClient;

    private final String[] descriptionSearchFields = {"summary", "summary._2gram", "summary._3gram"};

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Boolean delete(Book book) {
        bookRepository.delete(book);
        return Boolean.TRUE;
    }

	public Optional<Book> findById(String id) {
		return bookRepository.findById(id);
	}

    @SneakyThrows
    public BooksQueryDTO findBooks(String title, String summary, String genre, String isbn,Integer rating, boolean aggregate) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(genre)) {
            querySpec.must(QueryBuilders.termQuery("genre", genre));
        }

        if (!StringUtils.isEmpty(title)) {
            querySpec.must(QueryBuilders.matchQuery("title", title));
        }

        if (!StringUtils.isEmpty(isbn)) {
            querySpec.must(QueryBuilders.matchQuery("isbn", isbn));
        }

        if (!ObjectUtils.isEmpty(rating)) {
            querySpec.must(QueryBuilders.termQuery("rating", rating));
        }

        if (!StringUtils.isEmpty(summary)) {
            querySpec.must(QueryBuilders.multiMatchQuery(summary, descriptionSearchFields).type(Type.BOOL_PREFIX));
        }

        //Si no he recibido ningun parametro, busco todos los elementos.
        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        //Filtro implicito
        //No le pido al usuario que lo introduzca pero lo aplicamos proactivamente en todas las peticiones
        //En este caso, que los productos sean visibles (estado correcto de la entidad)
        querySpec.must(QueryBuilders.termQuery("visible", true));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        if (aggregate) {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("Genre Aggregation").field("genre").size(1000));
            // Mas info sobre size 1000 - https://www.elastic.co/guide/en/elasticsearch/reference/7.10/search-aggregations-bucket-terms-aggregation.html#search-aggregations-bucket-terms-aggregation-size
            nativeSearchQueryBuilder.withMaxResults(0);
        }

        //Opcionalmente, podemos paginar los resultados
        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 10));
        //Pagina 0, 10 elementos por pagina. El tam de pagina puede venir como qParam y tambien el numero de pagina

        Query query = nativeSearchQueryBuilder.build();
        SearchHits<Book> result = elasticClient.search(query, Book.class);

        List<AggregationDetailsDTO> responseAggs = new LinkedList<>();

        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();
            ParsedStringTerms genreAgg = (ParsedStringTerms) aggs.get("Genre Aggregation");

            //Componemos una URI basada en serverFullAddress y query params para cada argumento, siempre que no viniesen vacios
            String queryParams = getQueryParams(title, summary, genre, isbn,rating);
            genreAgg.getBuckets()
                    .forEach(
                            bucket -> responseAggs.add(
                                    new AggregationDetailsDTO(
                                            bucket.getKey().toString(),
                                            (int) bucket.getDocCount(),
                                            serverFullAddress + "/books?genre=" + bucket.getKey() + queryParams)));
        }
        return new BooksQueryDTO(result.getSearchHits().stream().map(SearchHit::getContent).toList(), responseAggs);
    }

    /**
     * Componemos una URI basada en serverFullAddress y query params para cada argumento, siempre que no viniesen vacios
     *
     * @param title        - title del libro
     * @param summary - summary del libro
     * @param genre     - genero del libro
     * @return
     */
    private String getQueryParams(String title, String summary, String genre, String isbn,Integer rating) {
        String queryParams = (StringUtils.isEmpty(title) ? "" : "&title=" + title)
                + (StringUtils.isEmpty(summary) ? "" : "&summary=" + summary)
                + (StringUtils.isEmpty(genre) ? "" : "&genre=" + genre) + (StringUtils.isEmpty(isbn) ? "" : "&isbn=" + isbn)
                + (ObjectUtils.isEmpty(rating) ? "" : "&rating=" + rating);
        // Eliminamos el ultimo & si existe
        return queryParams.endsWith("&") ? queryParams.substring(0, queryParams.length() - 1) : queryParams;
    }
}
