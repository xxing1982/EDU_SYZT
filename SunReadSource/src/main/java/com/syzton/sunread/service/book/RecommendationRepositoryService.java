package com.syzton.sunread.service.book;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Recommendation;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jerry on 3/18/15.
 */
@Service
public class RecommendationRepositoryService implements RecommendationService {

    private BookRepository bookRepository;

    private RecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationRepositoryService(BookRepository bookRepository, RecommendationRepository recommendationRepository) {
        this.bookRepository = bookRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public void recommend(Long bookId) {

        Book book = bookRepository.findOne(bookId);

        if(book == null)
            throw new NotFoundException("book with id:"+ bookId +" entity dose not found..");

        Recommendation recommendation = new Recommendation();
        recommendation.setBook(book);

        recommendationRepository.save(recommendation);

    }

}
