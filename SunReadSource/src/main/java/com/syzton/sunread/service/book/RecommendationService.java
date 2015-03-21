package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Created by jerry on 3/8/15.
 */
public interface RecommendationService {

    public void recommend(Long bookId);


}
