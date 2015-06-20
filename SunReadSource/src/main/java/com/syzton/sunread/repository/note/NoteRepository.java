package com.syzton.sunread.repository.note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.user.User;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

/**
 * @author chenty
 *
 */
public interface NoteRepository extends JpaRepository<Note,Long>,QueryDslPredicateExecutor<Note> {

	Page<Note> findByBook(Book book, Pageable pageable);

	Page<Note> findByUser(User user, Pageable pageable);
	
	Page<Note> findAll(Pageable pageable);
	
	@Query("select u from Note u where u.book in :bookList")
	Page<Note> findAllByBookList(@Param("bookList")Collection<Book> bookList, Pageable pageable);

	@Query("SELECT Distinct(b) FROM Note b WHERE b.user = (:user) AND b.creationTime between (:startTime) AND (:endTime)")
	List<Note> findByUserAndSemester(@Param("user")User user, @Param("startTime")DateTime startTime, @Param("endTime")DateTime endTime);

}
