package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.repository.book.BookRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Petri Kainulainen
 */
@Service
public class BookRepositoryService implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryService.class);
    private BookRepository repository;

    @Autowired
    public BookRepositoryService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book add(BookDTO added) {
        LOGGER.debug("Adding a new Book entry with information: {}", added);

        Book bookModel = Book.getBuilder(added.getIsbn(), added.getName())
                .description(added.getDescription())
                .build();
        return repository.save(bookModel);
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Book findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a book entry with id: {}", id);

        Book found = repository.findOne(id);
        LOGGER.debug("Found book entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No book found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Book deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Book deleted = findById(id);
        LOGGER.debug("Deleting to-do entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

}
