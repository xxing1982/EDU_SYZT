package com.syzton.sunread.controller.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.service.book.BookService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Jerry Zhang
 * @date 2015-03-05
 */
@Controller
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    @Autowired
    public BookController(BookService service) {
        this.bookService = service;
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.POST)
    @ResponseBody
    public BookDTO add(@Valid @RequestBody BookDTO dto) {
        LOGGER.debug("Adding a new book entry with information: {}", dto);

        Book added = bookService.add(dto);

        LOGGER.debug("Added a book entry with information: {}", added);

        return added.createDTO();
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Book> findAllBooks(@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<Book> pageResult = bookService.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }


    @RequestMapping(value = "/api/books/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Book found = bookService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO();
    }



    @RequestMapping(value = "/api/books/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Book deleted = bookService.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO();
    }
}
