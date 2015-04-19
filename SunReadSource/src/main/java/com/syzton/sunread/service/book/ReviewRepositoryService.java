package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Review;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.ReviewRepository;
import com.syzton.sunread.repository.user.StudentRepository;
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
    private ReviewRepository reviewRepository;

    private BookRepository bookRepository;

    private StudentRepository studentRepository;

    @Autowired
    public ReviewRepositoryService(ReviewRepository reviewRepository, BookRepository bookRepository, StudentRepository studentRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Review findById(Long id) {

        Review found = reviewRepository.findOne(id);

        if (found == null) {
            throw new NotFoundException("No review-entry found with id: " + id);
        }

        return found;
    }
    @Transactional
    @Override
    public Review add(ReviewDTO reviewDTO,long bookId) {

        Book book = bookRepository.findOne(bookId);

        if(book == null){
            throw new NotFoundException(" book with id ="+bookId +" not found...");
        }
        Student student = studentRepository.findOne(reviewDTO.getStudentId());
        if(student == null){
            throw new NotFoundException(" student with id = "+reviewDTO.getStudentId() +" not found...");
        }
        Review exits = reviewRepository.findByStudentIdAndBook(student.getId(),book);
        if(exits != null){
            throw new DuplicateException("student with name =" +student.getUsername()+" is already reviewed");
        }
        Review review = Review.getBuilder(reviewDTO.getStudentId(),reviewDTO.getTitle(),reviewDTO.getContent(),student.getUsername()).book(book).rate(reviewDTO.getRate()).build();

        Review added = reviewRepository.save(review);

        Number avgRate = reviewRepository.countReviewByBook(book);

        book.setAvgRate(avgRate.intValue());

        bookRepository.save(book);


        return added;
    }
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Review deleteById(Long id) {
        Review review = reviewRepository.findOne(id);
        reviewRepository.delete(review);
        return review;
    }

    @Override
    public boolean isReviewed(long studentId, long bookId) {
        Book book = bookRepository.findOne(bookId);

        if(book == null){
            throw new NotFoundException(" book with id ="+bookId +" not found...");
        }
        Student student = studentRepository.findOne(studentId);
        if(student == null){
            throw new NotFoundException(" student with id = "+studentId +" not found...");
        }
        Review exits = reviewRepository.findByStudentIdAndBook(student.getId(),book);
        if(exits != null){
            throw new DuplicateException("student with name =" +student.getUsername()+" is already reviewed");
        }
        return true;
    }

    @Transactional
    @Override
    public Page<Review> findByBookId(Pageable pageable, long bookId) {

        Book book = bookRepository.findOne(bookId);
        Page<Review> reviewPage = reviewRepository.findByBook(book,pageable);
        return reviewPage;
    }
}
