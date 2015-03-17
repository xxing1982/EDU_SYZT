package com.syzton.sunread.controller.bookshelf;

import javassist.NotFoundException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.dto.bookshelf.BookShelfOperationDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.bookshelf.BookShelfOperation;
import com.syzton.sunread.service.bookshelf.BookShelfOperationService;


/*
 * @Date 2015/03/15
 * @Author Morgan-Leon 
 */
@Controller
@RequestMapping(value = "/api")
public class BookShelfOperationController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(BookShelfOperationController.class);

    private BookShelfOperationService service;

    @Autowired
    public BookShelfOperationController(BookShelfOperationService service) {
        this.service = service;
    }

//Add an operation 
    @RequestMapping(value = "/bookshelfoperation", method = RequestMethod.POST)
    @ResponseBody
    public BookShelfOperationDTO add(@Valid @RequestBody BookShelfOperationDTO dto) {
        LOGGER.debug("Adding a new operation entry with information: {}", dto);

        BookShelfOperation added = service.add(dto);

        LOGGER.debug("Added an operation entry with information: {}", added);

        return added.createDTO(added);
    }

//Delete an operation
    @RequestMapping(value = "/bookshelfoperation/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookShelfOperationDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        BookShelfOperation deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update an operation
    @RequestMapping(value = "/bookshelfoperation/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BookShelfOperationDTO update(@Valid @RequestBody BookShelfOperationDTO dto) throws NotFoundException {
        LOGGER.debug("Adding a new operation entry with information: {}", dto);
        
        BookShelfOperation updated = service.update(dto);
        LOGGER.debug("Added a operation entry with information: {}", updated);             
       return updated.createDTO(updated);
    }
 
 //Get all operations
    @RequestMapping(value = "/bookshelfoperation", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<BookShelfOperation> findAllBooks(@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  bookshelfoperation in shelf entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<BookShelfOperation> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get an operation   
    @RequestMapping(value = "/bookshelfoperation/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookShelfOperationDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding operation in shelf entry with id: {}", id);

        BookShelfOperation found = service.findById(id);
        LOGGER.debug("Found operation entry with information: {}", found);

        return found.createDTO(found);
    }
    

}
