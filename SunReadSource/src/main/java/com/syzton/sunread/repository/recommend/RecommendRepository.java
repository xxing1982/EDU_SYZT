package com.syzton.sunread.repository.recommend;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.syzton.sunread.dto.recommend.RecommendDTO;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.recommend.Recommend;
import com.syzton.sunread.model.user.Teacher;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
public interface RecommendRepository extends JpaRepository<Recommend, Long>,QueryDslPredicateExecutor<Recommend> {
	
	Recommend findByBookinshelf(BookInShelf bookInShelf);
	
	ArrayList<Recommend> findByTeacher(Teacher teacher);
	
	Page<Recommend> findByTeacher(Teacher teacher,Pageable pageable);
	
//	@Query("SELECT Distinct(b) FROM BookInShelf b WHERE bookshelf=(:bookshelf) AND b.deleted=0 ORDER BY b.id DESC")
//	@Query("SELECT B.id, B.bookId,B.bookName FROM Recommend R , BookInShelf B where B.deleted = 0 group by B.bookName order by sum(B.bookshelf) desc;")
//	ArrayList<RecommendDTO> recommendBooks();
	
}
