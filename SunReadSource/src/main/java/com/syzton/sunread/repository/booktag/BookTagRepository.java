package com.syzton.sunread.repository.booktag;

import com.syzton.sunread.model.booktag.BookTag;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chenty
 *
 */
public interface BookTagRepository extends JpaRepository<BookTag,Long> {
}
