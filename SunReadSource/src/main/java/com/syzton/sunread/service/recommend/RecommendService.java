package com.syzton.sunread.service.recommend;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.recommend.RecommendDTO;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.recommend.Recommend;
import com.syzton.sunread.model.user.Teacher;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
public interface RecommendService {
	
	
	
//    public Recommend deleteById(Long id) throws NotFoundException;

    public Recommend findById(Long id) throws NotFoundException;

    Page<Recommend> findAll(Pageable pageable) throws NotFoundException;

	/**
	 * @param recommendDTO
	 * @param teacherId
	 * @param bookInShelfId
	 * @return
	 */
	RecommendDTO add(RecommendDTO recommendDTO, Long teacherId, Long bookInShelfId);
}
