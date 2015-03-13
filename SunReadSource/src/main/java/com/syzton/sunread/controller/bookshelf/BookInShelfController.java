package com.syzton.sunread.controller.bookshelf;
import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.service.bookshelf.BookInShelfService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javassist.NotFoundException;

import javax.validation.Valid;

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
    @RequestMapping(value = "/api/bookinshelf", method = RequestMethod.POST)
    @ResponseBody
    public BookInShelfDTO add(@Valid @RequestBody BookInShelfDTO dto) {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        BookInShelf added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);
              
       return added.createDTO(added);
    }
   
    
//Delete a book in shelf
    @RequestMapping(value = "/api/bookinshelf/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookInShelfDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        BookInShelf deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update a book in shelf    
    @RequestMapping(value = "/api/bookinshelf/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BookInShelfDTO update(@Valid @RequestBody BookInShelfDTO dto) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        BookInShelf updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    }
   
//Get all books in shelf
    @RequestMapping(value = "/api/bookinshelf", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<BookInShelf> findAllBooks(@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  books in shelf entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<BookInShelf> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a Book in bookshelf    
    @RequestMapping(value = "/api/bookinshelf/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookInShelfDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a book in shelf entry with id: {}", id);

        BookInShelf found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO(found);
    }
    
}



