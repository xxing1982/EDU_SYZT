package com.syzton.sunread.service.book;

import com.syzton.sunread.assembler.book.BookAssembler;
import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.syzton.sunread.repository.book.predicates.BookPredicates.findByCondition;
import static com.syzton.sunread.repository.book.predicates.BookPredicates.quickSearchContains;

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


    @Transactional(rollbackFor = {DuplicateException.class})
    @Override
    public BookDTO add(BookDTO bookDTO) {

        Book exits = bookRepo.findByIsbn(bookDTO.getIsbn());
        if(exits != null){
            throw new DuplicateException("book with isbn: "+bookDTO.getIsbn()+" is already exits..");
        }

        BookAssembler assembler = new BookAssembler();
        Book book =  assembler.fromDTOtoBookWithExtra(bookDTO);

        LOGGER.debug(book.toString());

        book = bookRepo.save(book);
        bookDTO.setId(book.getId());
        return bookDTO;
    }

    @Override
    public Book findById(Long id) {
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
    public Book deleteById(Long id){
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Book deleted = findById(id);
        LOGGER.debug("Deleting to-do entry: {}", deleted);
        if(deleted == null)
            throw new NotFoundException("No book found with id: " + id);

        bookRepo.delete(deleted);
        return deleted;
    }
    
    @Override
    public Page<Book> findAll(Pageable pageable){

        Page<Book> bookPages = bookRepo.findAll(pageable);

        return bookPages;

    }

//    @Transactional(rollbackFor = {NotFoundException.class})
//    @Override
//    public Page<Book> findByCategories(Set<Long> categoryIds,Pageable pageable){
//
//        Set<Category> categories = new HashSet<>();
//
//        for(Long id : categoryIds)
//        {
//            Category category = categoryRepo.findOne(id);
//            if(category == null){
//                throw new NotFoundException("no category be found wiht id: "+id+"");
//            }
//            categories.add(category);
//        }
//        Page<Book> bookPages = bookRepo.findByCategories(categories,pageable);
//
//        return bookPages;
//    }

    @Transactional(readOnly = true)
    @Override
    public Page<Book> quickSearch(String searchTerm, Pageable pageable) {

        Page<Book> bookPage = bookRepo.findAll(quickSearchContains(searchTerm), pageable);
        if(bookPage.getContent().size()==0)
            throw new NotFoundException("no satisfy book result with this search term : "+searchTerm);
        return bookPage;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Book> searchByCondition(BookExtraDTO condition,Pageable pageable){

        Page<Book> bookPage = bookRepo.findAll(findByCondition(condition),pageable);

        if(bookPage.getContent().size()==0)
            throw new NotFoundException("no satisfy book result with this condition ="+ condition.toString());
        return bookPage;

    }
}
