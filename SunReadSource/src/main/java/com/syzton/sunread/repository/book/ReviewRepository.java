package com.syzton.sunread.repository.book;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jerry on 3/9/15.
 */
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByBook(Book book, Pageable pageable);
}
