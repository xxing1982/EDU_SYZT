package com.syzton.sunread.repository.book;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by jerry on 3/8/15.
 */
public interface BookRepository extends JpaRepository<Book,Long>,QueryDslPredicateExecutor<Book>{

    @Query("SELECT Distinct(b) FROM Book b LEFT JOIN b.extra.categories c where c in (:categories)")
    Page<Book> findByCategories(@Param("categories") Collection<Category> categories,Pageable pageable);

    Book findByIsbn(String isbn);

}
