package com.syzton.sunread.controller.bookshelf;
import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.exception.bookshelf.BookshelfNotFoundException;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.service.bookshelf.BookInShelfService;
import com.syzton.sunread.service.bookshelf.BookshelfService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan-Leon
 */
@Controller
public class BookInShelfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookshelfController.class);
    private BookInShelfService service;
    
    @Autowired
    public BookInShelfController(BookInShelfService service){
    	this.service = service;
    }
    
//Add a Book to bookshelf    
    @RequestMapping(value = "/api/bookshelf/{userId}/bookinshelf/{book_id}", method = RequestMethod.POST)
    @ResponseBody
    public BookInShelfDTO add(@Valid @RequestBody BookInShelfDTO dto) {
        LOGGER.debug("Adding a new bookshelf entry with information: {}", dto);

        BookInShelf added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);
        
        dto.getBookshelf();
        

       return createDTO(added);
    }
    
	private BookInShelfDTO createDTO(BookInShelf added) {
		// TODO Auto-generated method stub
		return null;
	}
}



















