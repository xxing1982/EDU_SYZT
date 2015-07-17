package com.syzton.sunread.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.book.Book;

/**
 * Created by jerry on 3/8/15.
 */
public interface BookRepository extends JpaRepository<Book,Long>,QueryDslPredicateExecutor<Book>{


    Book findByIsbn(String isbn);
    
    @Query("select id from Book u where u.name like ('%' || :searchTerm || '%') ")
    List<Long> findAllIdBySearchTerm(@Param("searchTerm") String searchTerm);

}
