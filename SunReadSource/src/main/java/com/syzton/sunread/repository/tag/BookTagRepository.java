package com.syzton.sunread.repository.tag;

import com.syzton.sunread.model.tag.BookTag;
import com.syzton.sunread.model.tag.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface BookTagRepository extends JpaRepository<BookTag,Long> {
	Page<BookTag> findByTag(Tag tag, Pageable pageable);
}
