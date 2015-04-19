package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.model.book.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by jerry on 3/9/15.
 */
public interface ReviewService {

    public Review findById(Long id);

    public Review add(ReviewDTO reviewDTO,long bookId);

    Review deleteById(Long id);


    Page<Review> findByBookId(Pageable pageable, long bookId);
}
