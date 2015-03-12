package com.syzton.sunread.controller.bookshelf;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.exception.bookshelf.BookshelfNotFoundException;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.service.bookshelf.BookshelfService;

import javassist.NotFoundException;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan-Leon
 */
@Controller
public class BookshelfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookshelfController.class);

    private BookshelfService service;

    @Autowired
    public BookshelfController(BookshelfService service) {
        this.service = service;
    }
    


  //Get information of a bookshelf by id  
    @RequestMapping(value = "/api/bookshelf/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookshelfDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding bookshelf entry with id: {}", id);

        Bookshelf found = service.findById(id);
        LOGGER.debug("Found bookshelf entry with information: {}", found);
        
        return found.createDTO(found);
    }

    @RequestMapping(value = "/api/bookshelf/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BookshelfDTO update(@Valid @RequestBody BookshelfDTO dto, @PathVariable("id") Long bookshelfId) throws NotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", dto);

        Bookshelf updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated.createDTO(updated);
    }

}