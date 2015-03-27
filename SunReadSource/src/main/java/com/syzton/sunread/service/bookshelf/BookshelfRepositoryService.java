package com.syzton.sunread.service.bookshelf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.repository.bookshelf.BookInShelfRepository;
import com.syzton.sunread.repository.bookshelf.BookshelfRepository;

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

    @Autowired
    public BookshelfRepositoryService(BookshelfRepository repository,BookInShelfRepository bookInShelfRepository) {
        this.repository = repository;
        this.bookInShelfRepository = bookInShelfRepository;
    }
    
	@Override
	public Bookshelf add(BookshelfDTO added) {
		Bookshelf bookshelf = Bookshelf.getBuilder(added.getOwner())
				.description(added.getDescription()).build();			
		return repository.save(bookshelf);
	}
	
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
	public Bookshelf findById(Long id) {
        LOGGER.debug("Finding a bookshelf entry with id: {}", id);

        Bookshelf found = repository.findOne(id);
        
        LOGGER.debug("Found book entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No bookshelf found with id: " + id);
        }
        else {
        	ArrayList<BookInShelf> bookArray = bookInShelfRepository.findByBookShelf(found);
			Set<BookInShelf> bookSet = new HashSet<BookInShelf>();
			for (BookInShelf bookInShelf : bookArray) {
				bookSet.add(bookInShelf);
			}
			found.setBookInShelf(bookSet);
		}

        return found; 
	}

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
	public Bookshelf update(BookshelfDTO updated){
        LOGGER.debug("Updates the information of a bookshelf entry.: {}", updated);

        Bookshelf bookshelfModel = Bookshelf.getBuilder(updated.getOwner())
                .description(updated.getDescription())
                .build();
    	
		return repository.save(bookshelfModel);
	}




}
