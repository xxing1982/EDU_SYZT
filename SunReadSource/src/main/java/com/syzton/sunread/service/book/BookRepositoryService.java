package com.syzton.sunread.service.book;

import com.syzton.sunread.assembler.book.BookAssembler;
import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;

import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.CategoryRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
public class BookRepositoryService implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryService.class);

    private BookRepository bookRepo;

    private CategoryRepository categoryRepo;



    @Autowired
    public BookRepositoryService(BookRepository bookRepo,CategoryRepository categoryRepo)
    {
        this.bookRepo = bookRepo;
        this.categoryRepo = categoryRepo;
    }



    @Override
    public BookDTO add(BookDTO bookDTO) {
        BookAssembler assembler = new BookAssembler();
        Book book =  assembler.fromDTO(bookDTO,categoryRepo);
        book = bookRepo.save(book);
        bookDTO.setId(book.getId());
        return bookDTO;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Book findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a book entry with id: {}", id);

        Book found = bookRepo.findOne(id);
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

        bookRepo.delete(deleted);
        return deleted;
    }
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<Book> findAll(Pageable pageable) throws NotFoundException{

        Page<Book> bookPages = bookRepo.findAll(pageable);

        return bookPages;

    }


}
