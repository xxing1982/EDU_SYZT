package com.syzton.sunread.service.book;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Quality;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.QualityRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jerry on 3/18/15.
 */
@Service
public class QualityRepositoryService implements QualityService {

    private BookRepository bookRepository;

    private QualityRepository qualityRepository;

    @Autowired
    public QualityRepositoryService(BookRepository bookRepository, QualityRepository qualityRepository) {
        this.bookRepository = bookRepository;
        this.qualityRepository = qualityRepository;
    }

    @Override
    public void recommend(Long bookId) throws NotFoundException {

        Book book = bookRepository.findOne(bookId);

        Quality quality = qualityRepository.findByBook(book);

        quality.increaseRecommendation();

        qualityRepository.save(quality);

    }

    @Override
    public void passCountIncrease(Long bookId) throws NotFoundException {
        Book book = bookRepository.findOne(bookId);

        Quality quality = qualityRepository.findByBook(book);

        quality.increasePassCount();

        qualityRepository.save(quality);

    }

    @Override
    public void testCountIncrease(Long bookId) throws NotFoundException {
        Book book = bookRepository.findOne(bookId);

        Quality quality = qualityRepository.findByBook(book);

        quality.increaseTestCount();

        qualityRepository.save(quality);

    }
}
