package com.syzton.sunread.repository.bookshelf;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;

/*
 * @Date 2015/03/12
 * @Author Morgan-Leon
 */
public interface BookInShelfRepository extends JpaRepository<BookInShelf,Long>,QueryDslPredicateExecutor<BookInShelf>{
	@Query("SELECT Distinct(b) FROM BookInShelf b WHERE bookshelf=(:bookshelf) ")
	ArrayList<BookInShelf> findByBookShelf(@Param("bookshelf")Bookshelf bookshelf);
	@Query("SELECT Distinct(A) FROM BookInShelf A, Book B where  A.book = B.id AND A.bookshelf=(:bookshelf)")
	Page<BookInShelf> BooksInBookShelf(@Param("bookshelf")Bookshelf bookshelf,Pageable pageable);
	Page<BookInShelf> findByBookshelf(Bookshelf bookshelf,Pageable pageable);
}
