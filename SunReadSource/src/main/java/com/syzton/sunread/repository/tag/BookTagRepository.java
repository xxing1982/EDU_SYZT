package com.syzton.sunread.repository.tag;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.tag.BookTag;
import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.model.user.User;

/**
 * @author chenty
 *
 */
public interface BookTagRepository extends JpaRepository<BookTag,Long> {
	Page<BookTag> findByTag(Tag tag, Pageable pageable);
	List<BookTag> findByTagAndBookAndUser(Tag tag, Book book, User user);

	@Query("select distinct bt.book from BookTag bt inner join bt.tag  where bt.tag in :tagList")
	Page<Book> findAllByTagList(@Param("tagList")Collection<Tag> tagList, Pageable pageable);
}
