package com.syzton.sunread.repository.book;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by jerry on 3/8/15.
 */
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT b FROM Book b INNER JOIN b.categories c where c in (:categories)")
    List<Book> findByCategories(@Param("categories") Collection<Category> categories);


}
