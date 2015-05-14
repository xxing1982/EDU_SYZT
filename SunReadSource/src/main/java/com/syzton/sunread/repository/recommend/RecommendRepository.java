package com.syzton.sunread.repository.recommend;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.recommend.Recommend;
import com.syzton.sunread.model.user.Teacher;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
	
	Recommend findByBookinshelf(BookInShelf bookInShelf);
	
	ArrayList<Recommend> findByTeacher(Teacher teacher);
	
	Page<Recommend> findByTeacher(Teacher teacher,Pageable pageable);
}
