package com.syzton.sunread.controller.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.service.book.BookService;
import com.syzton.sunread.service.book.RecommendationService;
import org.hibernate.annotations.Parameter;
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
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry Zhang
 * @date 2015-03-05
 */
@Controller
@RequestMapping(value = "/api")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    private RecommendationService recommendationService;

    @Autowired
    public BookController(BookService bookService, RecommendationService qualityService) {
        this.bookService = bookService;
        this.recommendationService = qualityService;
    }


    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseBody
    public BookDTO add(@Valid @RequestBody BookDTO bookDTO) {
        LOGGER.debug("Adding a new book entry with information: {}", bookDTO);

        BookDTO added = bookService.add(bookDTO);

        LOGGER.debug("Added a book entry with information: {}", added);

        return added;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Book> findByCategories(@RequestParam(value = "categories", required = false) String categories,
                                               @RequestParam("page") int page,
                                               @RequestParam("size") int size,
                                               @RequestParam(value = "sortBy", required = false) String sortBy) {
        LOGGER.debug("Finding to-do entry with id: {}");

        Pageable pageable = getPageable(page, size, sortBy);
        Page<Book> pageResult;
        if (categories != null) {
            String[] cIds = categories.split(",");
            Set<Long> categoryIds = new HashSet<>();
            for (String id : cIds) {
                categoryIds.add(Long.parseLong(id));
            }
            pageResult = bookService.findByCategories(categoryIds, pageable);
        } else {
            pageResult = bookService.findAll(pageable);

        }

        return new PageResource<>(pageResult, "page", "size");
    }

    private Pageable getPageable(int page, int size, String sortBy) {
        sortBy = sortBy == null ? "id" : sortBy;
        return new PageRequest(
                page, size, new Sort(sortBy)
        );
    }

    @RequestMapping(value = "/books/search", method = RequestMethod.GET)
    @ResponseBody
    public Page<Book> quickSearch(@RequestParam("searchTerm") String searchTerm,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size,
                                  @RequestParam(value = "sortBy", required = false) String sortBy) {

        Pageable pageable = getPageable(page, size, sortBy);

        Page<Book> bookPage = bookService.quickSearch(searchTerm, pageable);

        return bookPage;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Book findById(@PathVariable("id") Long id) {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Book found = bookService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Book deleteById(@PathVariable("id") Long id) {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Book deleted = bookService.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted;
    }

    @RequestMapping(value = "/books/{id}/recommends", method = RequestMethod.PUT)
    @ResponseBody
    public void recommend(@PathVariable("id") Long id) {

        recommendationService.recommend(id);

    }

}
