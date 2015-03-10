package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.model.book.Review;
import javassist.NotFoundException;

/**
 * Created by jerry on 3/9/15.
 */
public interface ReviewService {

    public Review findById(Long id) throws NotFoundException;

    public Review add(ReviewDTO reviewDTO);

    Review deleteById(Long id);
}
