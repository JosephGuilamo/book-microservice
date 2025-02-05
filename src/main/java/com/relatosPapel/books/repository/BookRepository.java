package com.relatosPapel.books.repository;

import com.relatosPapel.books.domain.Book;
import com.relatosPapel.books.repository.utils.Consts;
import com.relatosPapel.books.repository.utils.SearchCriteria;
import com.relatosPapel.books.repository.utils.SearchOperation;
import com.relatosPapel.books.repository.utils.SearchStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

   default List<Book> findByFilters(String titulo, String autor, String categoria, String isbn, Integer valoracion, Boolean visible){
       SearchCriteria<Book> spec = new SearchCriteria<>();

       if (!ObjectUtils.isEmpty(titulo)) {
           spec.add(new SearchStatement(Consts.TITULO, titulo, SearchOperation.MATCH));
       }

       if (!ObjectUtils.isEmpty(autor)) {
           spec.add(new SearchStatement(Consts.AUTOR, autor, SearchOperation.MATCH));
       }

       if (!ObjectUtils.isEmpty(categoria)) {
           spec.add(new SearchStatement(Consts.CATEGORIA, categoria, SearchOperation.EQUAL));
       }

       if (!ObjectUtils.isEmpty(isbn)) {
           spec.add(new SearchStatement(Consts.ISBN, isbn, SearchOperation.MATCH));
       }

         if (!ObjectUtils.isEmpty(valoracion)) {
              spec.add(new SearchStatement(Consts.VALORACION, valoracion, SearchOperation.EQUAL));
         }

       if (visible != null) {
           spec.add(new SearchStatement(Consts.VISIBLE, visible, SearchOperation.EQUAL));
       }

       return findAll(spec);
    }
}