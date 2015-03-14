package com.syzton.sunread.repository.bookshelf;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.Bookshelf;

/**
 * Created by Morgan on 3/12/15.
 */
public interface BookshelfRepository extends JpaRepository<Bookshelf,Long> {
}
