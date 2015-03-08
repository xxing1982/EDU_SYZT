package com.syzton.sunread.controller.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.service.book.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

       return createDTO(added);
    }


    private BookDTO createDTO(Book model) {
        BookDTO dto = new BookDTO();

        return dto;
    }
}
