package com.syzton.sunread.service.bookshelf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.bookshelf.BookInShelfRepository;
import com.syzton.sunread.repository.bookshelf.BookshelfRepository;
import com.syzton.sunread.repository.user.StudentRepository;

/**
 * @author Morgan-Leon
 * @Date 2015年3月15日
 * 
 */
@Service
public class BookshelfRepositoryService implements BookshelfService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookshelfRepositoryService.class);
    private BookshelfRepository repository;
    private BookInShelfRepository bookInShelfRepository;
    private StudentRepository studentRepository;
	private Student student;
	private Bookshelf bookshelf;
    @Autowired
    public BookshelfRepositoryService(BookshelfRepository repository,BookInShelfRepository bookInShelfRepository
    		,StudentRepository studentRepository) {
        this.repository = repository;
        this.bookInShelfRepository = bookInShelfRepository;
        this.studentRepository = studentRepository;
    }
    
	@Override
	public Bookshelf add(BookshelfDTO added,Long userId) {
		student = studentRepository.findOne(userId);
		bookshelf = repository.findOne(userId);
		if (student == null) {
			throw new NotFoundException("Not Found Student with id: "+student.getId());
		}
		if(bookshelf != null){
			throw new DuplicateException("Bookshelf with id: "+bookshelf.getId()+" is already exits..");
		}
		bookshelf = Bookshelf.getBuilder(student.getUsername(),student.getId())
				.description(added.getDescription()).build();			
		return repository.save(bookshelf);
	}
	
	@Override
	public Boolean addBookshelfByStudent(Student student) {
		
		Bookshelf bookshelf = Bookshelf.getBuilder(student.getUsername(),student.getId())
				.build();			
		repository.save(bookshelf);
		return true;
	}
	
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Boolean deleteBookshelfByStudent(Student student) {
    	
  	
		repository.delete(student.getId());
		return true;
	}
	
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
	public Bookshelf findById(Long id) {
        LOGGER.debug("Finding a bookshelf entry with id: {}", id);

        Bookshelf found = repository.findOne(id);
        
        LOGGER.debug("Found book entry: {}", found);

//        if (found == null) {
//            throw new NotFoundException("No bookshelf found with id: " + id);
//        }
//        else {
//        	ArrayList<BookInShelf> bookArray = bookInShelfRepository.findByBookShelf(found);
//			Set<BookInShelf> bookSet = new HashSet<BookInShelf>();
//			for (BookInShelf bookInShelf : bookArray) {
//				bookSet.add(bookInShelf);
//			}
//			found.setBookInShelf(bookSet);
//		}
        ArrayList<BookInShelf> delList = new ArrayList<BookInShelf>();
        for (BookInShelf bookInShelf : found.getBooksInShelf()) {
			if (bookInShelf.getDeleted()) {
				delList.add(bookInShelf);
			}
		}
        
        found.getBooksInShelf().removeAll(delList);
        ArrayList<BookInShelf> revList = new ArrayList<BookInShelf>();
        
        List<BookInShelf> bookInShelfs = found.getBooksInShelf();
        
        int lastIndex = bookInShelfs.size() - 1;
        
        for (int i = 0; i < lastIndex; i++) {
			revList.add(bookInShelfs.get(lastIndex -i));
		}
        
        found.setBookInShelf(revList);
        
        return found; 
	}

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
	public Bookshelf update(BookshelfDTO updated){
        LOGGER.debug("Updates the information of a bookshelf entry.: {}", updated);

        Bookshelf bookshelfModel = Bookshelf.getBuilder(updated.getUsername(),updated.getId())
                .description(updated.getDescription())
                .build();
    	
		return repository.save(bookshelfModel);
	}
    
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
	@Override
	public Boolean updateBookshelfByStudent(Student student) {
    	
    	bookshelf = repository.findOne(student.getId());
		repository.delete(bookshelf);
		Bookshelf bookshelf = Bookshelf.getBuilder(student.getUsername(),student.getId())
				.build();	
		repository.saveAndFlush(bookshelf);
		return true;
	}



}
