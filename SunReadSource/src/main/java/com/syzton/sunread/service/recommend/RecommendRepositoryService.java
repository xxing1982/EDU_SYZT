package com.syzton.sunread.service.recommend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syzton.sunread.dto.recommend.RecommendDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.recommend.Recommend;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.bookshelf.BookInShelfRepository;
import com.syzton.sunread.repository.bookshelf.BookshelfRepository;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.recommend.RecommendRepository;
import com.syzton.sunread.repository.user.StudentRepository;
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
	private StudentRepository studentRepository;
	private BookInShelfRepository bookInShelfRepository;
	private BookRepository bookRepository;
	private BookshelfRepository bookshelfRepository;
	private ClazzRepository clazzRepository;
	private CampusRepository campusRepository;
	private Teacher teacher;
	private Book book;
	private Bookshelf bookshelf;
	
	@Autowired
	public RecommendRepositoryService(RecommendRepository recommendRepository,BookshelfRepository bookshelfRepository
			,TeacherRepository teacherRepositiry,StudentRepository studentRepository,ClazzRepository clazzRepository
			,CampusRepository campusRepository,BookInShelfRepository bookInShelfRepository,BookRepository bookRepository) {
		// TODO Auto-generated constructor stub
		this.recommendRepository = recommendRepository;
		this.bookshelfRepository = bookshelfRepository;
		this.teacherRepositiry = teacherRepositiry;
		this.studentRepository = studentRepository;
		this.clazzRepository = clazzRepository;
		this.campusRepository = campusRepository;
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
			RecommendDTO dto = RecommendDTO.getBuilder(book.getId(), book.getName(), teacherId, teacher.getUsername()
					,studentId, bookshelf.getUsername(),isInShelf.getBookAttribute(),isInShelf.getReadState(),0).build();
			return dto;
			
		}
		else {
			Recommend isRecommended = recommendRepository.findByBookinshelf(isInShelf);
			if(isRecommended == null){
				if (isInShelf.getDeleted()) {
					isInShelf.setDeleted(false);
				}	
				isInShelf.setBookAttribute(recommendDTO.getBookAttribute());
				bookInShelfRepository.flush();
				recommend = Recommend.getBuilder(teacher, isInShelf).build();
				recommendRepository.save(recommend);
				RecommendDTO dto = RecommendDTO.getBuilder(book.getId(), book.getName(), teacherId, teacher.getUsername()
						,studentId, bookshelf.getUsername(),isInShelf.getBookAttribute(),isInShelf.getReadState(),1).build();
				return dto;
			}
			else {
				int recommendState = 3;
				if (isInShelf.getDeleted()) 
					isInShelf.setDeleted(false);	
				if(!isInShelf.getBookAttribute()){
					isInShelf.setBookAttribute(recommendDTO.getBookAttribute());
					recommendState = 2;
				}
				bookInShelfRepository.flush();
				RecommendDTO dto = RecommendDTO.getBuilder(book.getId(), book.getName(), teacherId, teacher.getUsername()
						,studentId, bookshelf.getUsername(),isInShelf.getBookAttribute(),isInShelf.getReadState(),recommendState).build();
				return dto;
			}
			
		}	

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

	@Override
	public ArrayList<RecommendDTO> findByTeacher(Long teacherId) {
		// TODO Auto-generated method stub
		ArrayList<RecommendDTO>  recommendDTOs = new ArrayList<RecommendDTO>();
		teacher = teacherRepositiry.findOne(teacherId);
	 
		if(teacher == null)
			throw new NotFoundException("Not Found Teacher with id" + teacher.getId());
		
		ArrayList<Recommend> recommends = recommendRepository.findByTeacher(teacher);
		if(recommends.isEmpty()){
			throw new NotFoundException("Not Found any recommends from teacher :"+teacher.getUsername());
		}
		for (Recommend recommend : recommends) {
			BookInShelf bookInShelf =  recommend.getBookinshelf();
			Student student = studentRepository.findOne(bookInShelf.getBookShelf().getId());
			Campus campus = campusRepository.findOne(student.getCampusId());
			Clazz clazz = clazzRepository.findOne(student.getClazzId());
			RecommendDTO dto =  RecommendDTO.getBuilder(bookInShelf.getBookName(),campus.getName(),clazz.getName(),student.getUsername(),bookInShelf.getBookAttribute(),recommend.getDescription()).build();
			recommendDTOs.add(dto);
		}
		return recommendDTOs;
	}
	
	@Override
	public Page<RecommendDTO> findByTeacher(Long teacherId,Pageable pageable) {
		// TODO Auto-generated method stub
		ArrayList<RecommendDTO>  recommendDTOs = new ArrayList<RecommendDTO>();
		teacher = teacherRepositiry.findOne(teacherId);
	 
		if(teacher == null)
			throw new NotFoundException("Not Found Teacher with id" + teacher.getId());
		
		Page<Recommend> pageRecommends = recommendRepository.findByTeacher(teacher,pageable);
		List<Recommend> recommends = pageRecommends.getContent();
		if(recommends.isEmpty()){
			throw new NotFoundException("Not Found any recommends from teacher :"+teacher.getUsername());
		}
		for (Recommend recommend : recommends) {
			BookInShelf bookInShelf =  recommend.getBookinshelf();
			Student student = studentRepository.findOne(bookInShelf.getBookShelf().getId());
			Campus campus = campusRepository.findOne(student.getCampusId());
			Clazz clazz = clazzRepository.findOne(student.getClazzId());
			RecommendDTO dto =  RecommendDTO.getBuilder(bookInShelf.getBookName(),campus.getName(),clazz.getName(),student.getUsername(),bookInShelf.getBookAttribute(),recommend.getDescription()).build();
			recommendDTOs.add(dto);
		}
		Page<RecommendDTO> recommendDTOPage = new PageImpl<>(recommendDTOs,pageable,pageRecommends.getTotalElements());
		return recommendDTOPage;
	}
}




