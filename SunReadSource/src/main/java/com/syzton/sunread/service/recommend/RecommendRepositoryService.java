/**
 * 
 */
package com.syzton.sunread.service.recommend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syzton.sunread.dto.recommend.RecommendDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.model.recommend.Recommend;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.bookshelf.BookInShelfRepository;
import com.syzton.sunread.repository.bookshelf.BookshelfRepository;
import com.syzton.sunread.repository.recommend.RecommendRepository;
import com.syzton.sunread.repository.user.TeacherRepository;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
@Service
public class RecommendRepositoryService implements RecommendService{
	
	private RecommendRepository recommendRepository;
	private TeacherRepository teacherRepositiry;
	private BookInShelfRepository bookInShelfRepository;
	private BookRepository bookRepository;
	private BookshelfRepository bookshelfRepository;
	private Teacher teacher;
	private Book book;
	private Bookshelf bookshelf;
	
	@Autowired
	public RecommendRepositoryService(RecommendRepository recommendRepository,BookshelfRepository bookshelfRepository
			,TeacherRepository teacherRepositiry,BookInShelfRepository bookInShelfRepository,BookRepository bookRepository) {
		// TODO Auto-generated constructor stub
		this.recommendRepository = recommendRepository;
		this.bookshelfRepository = bookshelfRepository;
		this.teacherRepositiry = teacherRepositiry;
		this.bookInShelfRepository = bookInShelfRepository;
		this.bookRepository = bookRepository;
	}
	
	/* (non-Javadoc)
	 * @see com.syzton.sunread.service.recommend.RecommendService#add(com.syzton.sunread.model.user.Teacher, com.syzton.sunread.model.bookshelf.BookInShelf)
	 */
	@Override
	public RecommendDTO add(RecommendDTO recommendDTO,Long teacherId, Long studentId) {
		teacher = teacherRepositiry.findOne(teacherId);
		if(teacher == null)
			throw new NotFoundException("Not Found Teacher with id" + teacher.getId());
		book = bookRepository.findOne(recommendDTO.getBookId());
		if(book == null)
			throw new NotFoundException("Not Found Book with id" + book.getId());
		bookshelf = bookshelfRepository.findOne(studentId);
		if(bookshelf == null)
			throw new NotFoundException("Not Found Bookshelf with id" + bookshelf.getId());
		BookInShelf isInShelf = bookInShelfRepository.findOneByBookshelfAndBookId(bookshelf, recommendDTO.getBookId());
		Recommend recommend = new Recommend();
		if (isInShelf == null) {
			isInShelf = BookInShelf.getBuilder(book.getId(),book.getName()
	        		,book.getIsbn(),book.getPictureUrl(),book.getAuthor(),book.getPoint()
	        		,bookshelf,recommendDTO.getBookAttribute(),recommendDTO.getReadState()).build();
			recommend = Recommend.getBuilder(teacher, isInShelf).build();
			bookInShelfRepository.save(isInShelf);
			recommendRepository.save(recommend);
		}
		else {
			if (isInShelf.getDeleted()) {
				isInShelf.setDeleted(false);
				isInShelf.setBookAttribute(recommendDTO.getBookAttribute());
				bookInShelfRepository.save(isInShelf);
			}
			recommend = Recommend.getBuilder(teacher, isInShelf).build();
			recommendRepository.save(recommend);
		}	
		RecommendDTO dto = RecommendDTO.getBuilder(book.getId(), book.getName(), teacherId, teacher.getUsername()
				,studentId, bookshelf.getUsername(),isInShelf.getBookAttribute(),isInShelf.getReadState()).build();
		
		return dto;
	}

	/* (non-Javadoc)
	 * @see com.syzton.sunread.service.recommend.RecommendService#findById(java.lang.Long)
	 */
	@Override
	public Recommend findById(Long id) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.syzton.sunread.service.recommend.RecommendService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Recommend> findAll(Pageable pageable) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
