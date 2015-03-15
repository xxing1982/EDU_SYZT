package com.syzton.sunread.repository.bookshelf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;

/*
 * @Date 2015/03/12
 * @Author Morgan-Leon
 */
public interface BookInShelfRepository extends JpaRepository<BookInShelf,Long>{
	Page<BookInShelf> findByBookshelf(Bookshelf bookshelf,Pageable pageable);
}
