package com.syzton.sunread.repository.bookshelf;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;

/*
 * @Date 2015/03/12
 * @Author Morgan-Leon
 */
public interface BookInShelfRepository extends JpaRepository<BookInShelf,Long>,QueryDslPredicateExecutor<BookInShelf>{
	@Query("SELECT Distinct(b) FROM BookInShelf b WHERE bookshelf=(:bookshelf) ORDER BY b.id DESC")
	ArrayList<BookInShelf> findByBookShelf(@Param("bookshelf")Bookshelf bookshelf);
	Page<BookInShelf> findByBookshelf(Bookshelf bookshelf,Pageable pageable);
	@Query("SELECT Distinct(b) FROM BookInShelf b WHERE bookshelf=(:bookshelf) AND bookId=(:bookId)")
	BookInShelf findOneByBookshelfAndBookId(@Param("bookshelf")Bookshelf bookshelf,@Param("bookId")Long bookId);
	ArrayList<BookInShelf> findByBookId(Long booId);
	@Query("SELECT Distinct(b) FROM BookInShelf b WHERE b.bookshelf = (:studentId) AND b.creationTime between (:startTime) AND (:endTime)")
	ArrayList<BookInShelf> findByStudentIdAndSemester(@Param("studentId")Long studentId, @Param("startTime")DateTime startTime, @Param("endTime")DateTime endTime);
}
