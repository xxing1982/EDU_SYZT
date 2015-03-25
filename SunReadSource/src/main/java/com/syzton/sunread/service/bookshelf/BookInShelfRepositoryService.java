package com.syzton.sunread.service.bookshelf;

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
    public BookInShelfRepositoryService(BookInShelfRepository repository) {
        this.repository = repository;
    }
    
    @Transactional
	@Override
	public BookInShelf add(BookInShelfDTO added, long id, long bookId) {
		// TODO Auto-generated method stub
        LOGGER.debug("Adding a new Book entry with information: {}", added);
        
        Bookshelf bookshelf = bookshelfRepository.findOne(id);
        Book book = bookRepository.findOne(bookId);
        BookInShelf bookInShelfModel = BookInShelf.getBuilder(book,bookshelf
        		,added.getBookAttribute(),added.getReadState())
        		.description(added.getDescription())
        		.build();
             
        return repository.save(bookInShelfModel);
	}
	
	@Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf deleteById(long id) throws NotFoundException {
		// TODO Auto-generated method stub
		BookInShelf bookInShelf = repository.findOne(id);
		repository.delete(bookInShelf);
		return bookInShelf;
	}
	
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf findById(Long id) throws NotFoundException {
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
    public Page<BookInShelf> findByBookshelfId(Pageable pageable,long id) throws NotFoundException{
    	
    	Bookshelf bookshelf = bookshelfRepository.findOne(id);
        Page<BookInShelf> bookPages = repository.findByBookshelf(bookshelf,pageable);

        return bookPages;
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookInShelf update(BookInShelfDTO updated,long id) throws NotFoundException {
		// TODO Auto-generated method stub
        LOGGER.debug("Adding a new Book entry with information: {}", updated);
        
        BookInShelf bookInShelf = repository.findOne(id);
        
        bookInShelf.update(updated.getDescription(), updated.getBookAttribute()
        		,updated.getReadState());
        
        return bookInShelf;
	}

}
