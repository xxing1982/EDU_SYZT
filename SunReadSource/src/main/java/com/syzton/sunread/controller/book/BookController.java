package com.syzton.sunread.controller.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.service.book.BookService;
import com.syzton.sunread.todo.dto.TodoDTO;
import com.syzton.sunread.todo.exception.TodoNotFoundException;
import com.syzton.sunread.todo.model.Todo;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**@date 2015-03-05
 * @author Jerry Zhang
 */
@Controller
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    @Autowired
    public BookController(BookService service) {
        this.bookService = service;
    }

    @RequestMapping(value = "/api/book", method = RequestMethod.POST)
    @ResponseBody
    public BookDTO add(@Valid @RequestBody BookDTO dto) {
        LOGGER.debug("Adding a new book entry with information: {}", dto);

        Book added = bookService.add(dto);

        LOGGER.debug("Added a book entry with information: {}", added);

       return added.createDTO(added);
    }


    @RequestMapping(value = "/api/book/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Book found = bookService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO(found);
    }

    @RequestMapping(value = "/api/book/{id}/review", method = RequestMethod.GET)
    @ResponseBody
    public BookDTO findByIdwithReview(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Book found = bookService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.bookWithReview(found);
    }
    @RequestMapping(value = "/api/book/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Book deleted = bookService.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
}
