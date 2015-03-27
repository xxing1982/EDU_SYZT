package com.syzton.sunread.service.bookshelf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
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

    
    @Autowired
    public BookInShelfRepositoryService(BookInShelfRepository repository
    		,BookshelfRepository bookshelfRepository,BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
        this.bookshelfRepository = bookshelfRepository;
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf add(BookInShelfDTO added, Long id, Long bookId) {
		// TODO Auto-generated method stub
        LOGGER.debug("Adding a new Book entry with information: {}", added);
        
        Book book = new Book();
        Bookshelf bookshelf = new Bookshelf();
        book = bookRepository.findOne(bookId);
        bookshelf = bookshelfRepository.findOne(id);
        if (book == null) {
			throw new NotFoundException("no book found with isbn :"+ book.getIsbn());
		}
        if (bookshelf == null) {
			throw new NotFoundException("no bookshelf found with id :"+bookshelf.getId());
		}
        BookInShelf bookInShelfModel = BookInShelf.getBuilder(book,bookshelf
        		,added.getBookAttribute(),added.getReadState())
        		.description(added.getDescription())
        		.build();
        BookInShelf model = repository.save(bookInShelfModel);
        return model;
	}
	
	@Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf deleteById(long id){
		// TODO Auto-generated method stub
		BookInShelf bookInShelf = repository.findOne(id);
		repository.delete(bookInShelf);
		return bookInShelf;
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
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<BookInShelf> findByBookshelfId(Pageable pageable,long id) {
    	
    	Bookshelf bookshelf = bookshelfRepository.findOne(id);
        Page<BookInShelf> bookPages = repository.findByBookshelf(bookshelf,pageable);

        return bookPages;
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
