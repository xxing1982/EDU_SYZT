package com.syzton.sunread.service.bookshelf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.exception.bookshelf.BookInShelfDuplicateVerifiedException;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.repository.SemesterRepository;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.bookshelf.BookInShelfRepository;
import com.syzton.sunread.repository.bookshelf.BookshelfRepository;
import com.syzton.sunread.service.book.BookRepositoryService;

/*
 * @Date 2015/3/12
 * @author Morgan-Leon
 */
@Service
public class BookInShelfRepositoryService implements BookInShelfService{

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryService.class);
    @Autowired
    private BookInShelfRepository repository;
    private BookshelfRepository bookshelfRepository;
    private BookRepository bookRepository;
    private SemesterRepository semesterRepository;
	private Book book;
	private Bookshelf bookshelf;

    
    @Autowired
    public BookInShelfRepositoryService(BookInShelfRepository repository,SemesterRepository semesterRepository
    		,BookshelfRepository bookshelfRepository,BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.bookshelfRepository = bookshelfRepository;
        this.semesterRepository = semesterRepository;
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf add(BookInShelfDTO added, Long id, Long bookId) {
		// TODO Auto-generated method stub
        LOGGER.debug("Adding a new Book entry with information: {}", added);
       
        book = bookRepository.findOne(bookId);
        bookshelf = bookshelfRepository.findOne(id);
        if (book == null) {
			throw new NotFoundException("no book found with isbn :"+ book.getIsbn());
		}
        if (bookshelf == null) {
			throw new NotFoundException("no bookshelf found with id :"+bookshelf.getId());
		}
        
        //If the Book has been added in student`s bookshelf
//		ArrayList<BookInShelf> bookArray = repository.findByBookShelf(bookshelf);
//		for (BookInShelf bookInShelf : bookArray) {
//			if(bookInShelf.getBookId() == bookId){
//				if(bookInShelf.getDeleted()){
//					bookInShelf.setDeleted(false);
//					return repository.save(bookInShelf);
//				}
//				else{
//					throw new DuplicateException("Book with name: "+book.getName()+" is already in your bookshelf..");
//				}
//			}	
//		}
        
        BookInShelf isInShelf = repository.findOneByBookshelfAndBookId(bookshelf, bookId);
        if(isInShelf == null){
            BookInShelf bookInShelfModel = BookInShelf.getBuilder(book.getId(),book.getName()
            		,book.getIsbn(),book.getPictureUrl(),book.getAuthor(),book.getPoint()
            		,bookshelf,added.getBookAttribute(),added.getReadState())
            		.description(added.getDescription())
            		.build();
            BookInShelf model = repository.save(bookInShelfModel);
            return model;
        }
        else {
			if(isInShelf.getDeleted()){
				isInShelf.setDeleted(false);
				return repository.save(isInShelf);
			}
			else{
				throw new DuplicateException("Book with name: "+book.getName()+" is already in your bookshelf..");
			}
		}
        
	}
	
	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf deleteById(long id){
		BookInShelf deleted = repository.findOne(id);
		deleted.setDeleted(true);
		repository.save(deleted);
		return deleted;
	}
	
	
	@Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf deleteByBookshelfIdAndBookId(long bookshelfId,Long bookId){
		// TODO Auto-generated method stub
		Bookshelf bookshelf = bookshelfRepository.findOne(bookshelfId);
		BookInShelf bookInShelf = repository.findOneByBookshelfAndBookId(bookshelf, bookId);
		//To make sure no one is able to delete a book that has been verified
		if(bookInShelf.getDeleted()&&!bookInShelf.getReadState()){
			repository.delete(bookInShelf);;
		}
		else {
			bookInShelf.setDeleted(true);
			repository.save(bookInShelf);
		}
		return bookInShelf;
	}
	
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public boolean deleteByBookId(Long bookId){
        book = bookRepository.findOne(bookId);
        if (book == null) {
			throw new NotFoundException("no book found with isbn :"+ book.getIsbn());
		}
        ArrayList<BookInShelf> booksInShelf = repository.findByBookId(bookId);
        for (BookInShelf bookInShelf : booksInShelf) {
			if(bookInShelf != null){
		        LOGGER.warn("delete a Book  with information: {}", bookInShelf);
		        bookInShelf.setDeleted(true);
				repository.save(bookInShelf);
				continue;
			}
			else {
				throw new NotFoundException("book with id :"+bookId+"is not in the shelf");
			}			
		}
        return true;
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public boolean deleteByBookshelf(Bookshelf bookshelf){

        ArrayList<BookInShelf> booksInShelf = repository.findByBookShelf(bookshelf);
        for (BookInShelf bookInShelf : booksInShelf) {
			if(bookInShelf != null){
		        LOGGER.warn("delete a Book  with information: {}", bookInShelf);
		        bookInShelf.setDeleted(true);
				repository.save(bookInShelf);
				continue;
			}
			else {
				throw new NotFoundException("bookshelf with id :"+bookshelf.getId()+"is not contain any books");
			}			
		}
        return true;
    }
	
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf findById(Long id)  {
        LOGGER.debug("Finding a bookinshelf entry with id: {}", id);
		BookInShelf found = repository.findOne(id);
        LOGGER.debug("Found book entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No book found with id: " + id);
        }
        return found;
	}
    
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf findByStudentIdAndBookId(Long id,Long bookId)  {
        LOGGER.debug("Finding a bookinshelf entry with id: {}", id);
		Bookshelf bookshelf = bookshelfRepository.findOne(id);
        
        if (bookshelf == null) {
            throw new NotFoundException("No bookshelf found with id: " + id);
        }
        
        BookInShelf bookInShelf = repository.findOneByBookshelfAndBookId(bookshelf, bookId);
        return bookInShelf;
	}
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<BookInShelf> findByBookshelfId(Pageable pageable,long id) {
    	
    	Bookshelf bookshelf = bookshelfRepository.findOne(id);
		if (bookshelf == null) {
			throw new NotFoundException("no bookshelf found with id :" + id);
		}
	    Page<BookInShelf> bookPages = repository.findByBookshelf(bookshelf, pageable);    
	    return bookPages;
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public ArrayList<BookInShelf> findByStudentIdAndSemester(Long studentId, DateTime startTime,DateTime endTime) {
    	Bookshelf bookshelf = bookshelfRepository.findOne(studentId);
	    ArrayList<BookInShelf> booksInSemester = repository.findByStudentIdAndSemester(bookshelf, startTime, endTime);  
	    return booksInSemester;
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf update(BookInShelfDTO updated,long id){
		// TODO Auto-generated method stub
        LOGGER.debug("Adding a new Book entry with information: {}", updated);
        
        BookInShelf bookInShelf = repository.findOne(id);
        
        bookInShelf.update(updated.getDescription(), updated.getBookAttribute()
        		,updated.getReadState());
        
        return bookInShelf;
	}
    
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public boolean updateReadState(Long studentId,Long bookId) throws BookInShelfDuplicateVerifiedException{

    	Bookshelf bookshelf = bookshelfRepository.findOne(studentId);
    	BookInShelf bookInShelf = repository.findOneByBookshelfAndBookId(bookshelf, bookId);
        if(bookInShelf == null)
        	throw new NotFoundException("The book with name "+bookInShelf.getBookName()+"is not in the bookshelf");
        if(bookInShelf.getDeleted())
        	throw new NotFoundException("The book with name"+bookInShelf.getBookName()+"had been deleted");
    	if (bookInShelf.updateReadState()) {
    		repository.saveAndFlush(bookInShelf);
			return true;
		}
        else {
    		throw new BookInShelfDuplicateVerifiedException("The book with id :"+bookId+"has been verified");
		}
    }
    
    //Alter Bookinshelf when book changed
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public boolean updateByBookId(Long bookId){
    	book = bookRepository.findOne(bookId);
        if (book == null) {
			throw new NotFoundException("no book found with isbn :"+ book.getIsbn());
		}    	
        ArrayList<BookInShelf> booksInShelf = repository.findByBookId(bookId);
        for (BookInShelf bookInShelf : booksInShelf) {
			if(bookInShelf.updateByBook(book)){
				repository.saveAndFlush(bookInShelf);
				continue;
			}
			else {
				throw new NotFoundException("The book with Id"+bookId+"not in the bookshelf with Id:"
			+bookInShelf.getBookShelf().getId());
			}			
		}
        return true;
    }
    

	/* (non-Javadoc)
	 * @see com.syzton.sunread.service.bookshelf.BookInShelfService#findByBookshelfId(long)
	 */
	@Override
	public Set<BookInShelf> findByBookshelfId(long id) {
		// TODO Auto-generated method stub
		Bookshelf bookshelf = bookshelfRepository.findOne(id);
		if (bookshelf == null) {
			throw new NotFoundException("no bookshelf found with id :" + id);
		}
		else {
			ArrayList<BookInShelf> bookArray = repository.findByBookShelf(bookshelf);
			Set<BookInShelf> bookSet = new HashSet<BookInShelf>();
			for (BookInShelf bookInShelf : bookArray) {
				bookSet.add(bookInShelf);
			}
			return bookSet;
		}
		
	}
	
	

}
