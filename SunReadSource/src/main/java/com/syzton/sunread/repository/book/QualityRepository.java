package com.syzton.sunread.repository.book;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Category;
import com.syzton.sunread.model.book.Quality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Created by jerry on 3/8/15.
 */
public interface QualityRepository extends JpaRepository<Quality,Long> {

    public Quality findByBook(Book book);

}
