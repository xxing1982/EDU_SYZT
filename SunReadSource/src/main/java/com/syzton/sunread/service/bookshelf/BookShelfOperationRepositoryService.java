package com.syzton.sunread.service.bookshelf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.bookshelf.BookShelfOperationDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.bookshelf.BookShelfOperation;
import com.syzton.sunread.repository.bookshelf.BookShelfOperationRepository;

/*
 * @Date 2015/03/13
 * @Author Morgan-Leon
 */
@Service
public class BookShelfOperationRepositoryService  implements BookShelfOperationService{

    private static final Logger LOGGER = LoggerFactory.getLogger(BookShelfOperationRepositoryService.class);
    private BookShelfOperationRepository repository;

    @Autowired
    public BookShelfOperationRepositoryService(BookShelfOperationRepository repository) {
        this.repository = repository;
    }
	
	@Override
	public BookShelfOperation add(BookShelfOperationDTO added) {
		// TODO Auto-generated method stub
		BookShelfOperation model = BookShelfOperation.getBuilder(
				added.getBookshelf(), added.getOperationType())
				.description(added.getDescription()).build();
		return repository.save(model);
	}
	
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookShelfOperation deleteById(Long id) throws NotFoundException {
		// TODO Auto-generated method stub
        LOGGER.debug("Deleting a BookshelfOperation entry with id: {}", id);

        BookShelfOperation deleted = findById(id);
        LOGGER.debug("Deleting to-do entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
	}

    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookShelfOperation update(BookShelfOperationDTO updated)
			throws NotFoundException {
		// TODO Auto-generated method stub
		BookShelfOperation model = BookShelfOperation.getBuilder(
				updated.getBookshelf(), updated.getOperationType())
				.description(updated.getDescription()).build();
		return repository.save(model);
	}

    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public BookShelfOperation findById(Long id) throws NotFoundException {
		// TODO Auto-generated method stub
        LOGGER.debug("Finding a bookshelfOperation entry with id: {}", id);

        BookShelfOperation found = repository.findOne(id);
        LOGGER.debug("Found bookshelfOperation entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No book found with id: " + id);
        }
        return found;
	}
    
    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Page<BookShelfOperation> findAll(Pageable pageable)
			throws NotFoundException {
		// TODO Auto-generated method stub
        Page<BookShelfOperation> bookshelfOperationPages = repository.findAll(pageable);
		return bookshelfOperationPages;
	}

}
