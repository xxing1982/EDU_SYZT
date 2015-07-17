package com.syzton.sunread.service.book;

import static com.syzton.sunread.repository.book.predicates.TestPassPredicates.countTPDuringMonthly;
import static com.syzton.sunread.repository.book.predicates.TestPassPredicates.countTPDuringWeekly;
import static com.syzton.sunread.repository.book.predicates.TestPassPredicates.countTPDuringYearly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.TestPass;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.TestPassRepository;
import com.syzton.sunread.repository.user.UserRepository;

@Service
public class TestPassRepositoryService implements TestPassService {
	
	private BookRepository bookRepository;

    private TestPassRepository testPassRepository;

    private UserRepository userRepository;
    @Autowired
    public TestPassRepositoryService(BookRepository bookRepository, TestPassRepository testPassRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.testPassRepository = testPassRepository;
        this.userRepository = userRepository;
    }
	
	@Override
	public void hotBookUpdate(Long bookId, Long userId) {
		Book book = bookRepository.findOne(bookId);

        if(book == null)
            throw new NotFoundException("book with id:"+ bookId +" entity dose not found..");
        User user = userRepository.findOne(userId);
        if(user == null)
            throw new NotFoundException("user with id:"+ userId +" entity dose not found..");
        TestPass exitsWithUser = testPassRepository.findByBookIdAndUserId(bookId, userId);
        if(exitsWithUser != null){
            throw new DuplicateException("userId = "+userId+" is already recommended before...");
        }

        TestPass testPass = new TestPass();
        testPass.setBookId(bookId);
        testPass.setUserId(userId);

        testPassRepository.save(testPass);

        book.getStatistic().increaseHots();

        long weekHot = testPassRepository.count(countTPDuringWeekly(bookId));

        book.getStatistic().setWeeklyHot(weekHot);

        long monthHot = testPassRepository.count(countTPDuringMonthly(bookId));

        book.getStatistic().setMonthlyHot(monthHot);

        long yearHot = testPassRepository.count(countTPDuringYearly(bookId));
        book.getStatistic().setYearlyHot(yearHot);
        bookRepository.save(book);


	}

}
