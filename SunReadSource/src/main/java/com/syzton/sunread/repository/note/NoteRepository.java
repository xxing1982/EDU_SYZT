package com.syzton.sunread.repository.note;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.user.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author chenty
 *
 */
public interface NoteRepository extends JpaRepository<Note,Long>,QueryDslPredicateExecutor<Note> {

	Page<Note> findByBook(Book book, Pageable pageable);

	Page<Note> findByUser(User user, Pageable pageable);
	
	Page<Note> findAll(Pageable pageable);
	
}
