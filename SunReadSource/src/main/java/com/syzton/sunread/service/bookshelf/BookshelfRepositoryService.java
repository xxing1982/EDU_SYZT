package com.syzton.sunread.service.bookshelf;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.repository.bookshelf.BookshelfRepository;


@Service
public class BookshelfRepositoryService implements BookshelfService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookshelfRepositoryService.class);
    private BookshelfRepository repository;

    @Autowired
    public BookshelfRepositoryService(BookshelfRepository repository) {
        this.repository = repository;
    }
    
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
	public Bookshelf findById(Long id) throws NotFoundException {
		// TODO Auto-generated method stub
        LOGGER.debug("Finding a book entry with id: {}", id);

        Bookshelf found = repository.findOne(id);
        LOGGER.debug("Found book entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No book found with id: " + id);
        }

        return found; 
	}

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
	public Bookshelf update(BookshelfDTO updated)
			throws NotFoundException {
		// TODO Auto-generated method stub
        LOGGER.debug("Updates the information of a bookshelf entry.: {}", updated);

        Bookshelf bookshelfModel = Bookshelf.getBuilder(updated.getOwner(),updated.getBooksInShelf(),
        			updated.getBookShelfOperations())
                .description(updated.getDescription())
                .build();
    	
		return repository.save(bookshelfModel);
	}


}
