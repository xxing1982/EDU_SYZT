package com.syzton.sunread.repository.book;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jerry on 3/8/15.
 */
public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {

    public Recommendation findByBook(Book book);

}
