package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Review;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.ReviewRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jerry on 3/9/15.
 */
@Service
public class ReviewRepositoryService implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRepositoryService.class);
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Review findById(Long id) throws NotFoundException {

        Review found = reviewRepository.findOne(id);

        if (found == null) {
            throw new NotFoundException("No review-entry found with id: " + id);
        }

        return found;
    }
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Review add(ReviewDTO reviewDTO) {

        Book book = bookRepository.findOne(reviewDTO.getBookId());

        Review review = Review.getBuilder(reviewDTO.getContent()).book(book).build();

        return reviewRepository.save(review);
    }
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Review deleteById(Long id) {
        Review review = reviewRepository.findOne(id);
        reviewRepository.delete(review);
        return review;
    }
    @Transactional
    @Override
    public Page<Review> findByBookId(Pageable pageable, long bookId) {

        Book book = bookRepository.findOne(bookId);
        Page<Review> reviewPage = reviewRepository.findByBook(book,pageable);
        return reviewPage;
    }
}
