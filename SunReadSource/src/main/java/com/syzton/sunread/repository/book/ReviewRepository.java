package com.syzton.sunread.repository.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Review;

/**
 * Created by jerry on 3/9/15.
 */
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByBook(Book book, Pageable pageable);

    @Query(value = "select avg(r.rate) from Review r where r.book = (:book)")
    Number countReviewByBook(@Param("book") Book book);

    Review findByStudentIdAndBook(long studentId,Book book);

}
