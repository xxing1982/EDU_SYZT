package com.syzton.sunread.service.booktag;

import java.util.List;

import com.syzton.sunread.dto.booktag.BookTagDTO;
import com.syzton.sunread.exception.booktag.BookTagNotFoundException;
import com.syzton.sunread.model.booktag.BookTag;
import com.syzton.sunread.repository.booktag.BookTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author chenty
 *
 */
@Service
public class RepositoryBookTagService implements BookTagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryBookTagService.class);
    private BookTagRepository repository;

    @Autowired
    public RepositoryBookTagService(BookTagRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookTag add(BookTagDTO added) {
        LOGGER.debug("Adding a new booktag entry with information: {}", added);

        BookTag bookTagModel = BookTag.getBuilder()
                .build();
        return repository.save(bookTagModel);
    }
    
    @Transactional(rollbackFor = {BookTagNotFoundException.class})
    @Override
    public BookTag deleteById(Long id) throws BookTagNotFoundException {
        LOGGER.debug("Deleting a booktag entry with id: {}", id);

        BookTag deleted = findById(id);
        LOGGER.debug("Deleting booktag entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookTag> findAll() {
        LOGGER.debug("Finding all booktag entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {BookTagNotFoundException.class})
    @Override
    public BookTag findById(Long id) throws BookTagNotFoundException {
        LOGGER.debug("Finding a booktag entry with id: {}", id);

        BookTag found = repository.findOne(id);
        LOGGER.debug("Found booktag entry: {}", found);

        if (found == null) {
            throw new BookTagNotFoundException("No booktag entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {BookTagNotFoundException.class})
    @Override
    public BookTag update(BookTagDTO updated) throws BookTagNotFoundException {
        LOGGER.debug("Updating contact with information: {}", updated);

        BookTag model = findById(updated.getId());
        LOGGER.debug("Found a booktag entry: {}", model);

        model.update();

        return model;
    }
}
