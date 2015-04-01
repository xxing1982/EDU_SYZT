package com.syzton.sunread.repository.book;

import com.syzton.sunread.model.book.Recommendation;
import com.syzton.sunread.model.book.TestPass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by jerry on 3/8/15.
 */
public interface TestPassRepository extends JpaRepository<TestPass,Long>,QueryDslPredicateExecutor<TestPass> {

	public TestPass findByBookIdAndUserId(Long bookId, Long userId);

}
