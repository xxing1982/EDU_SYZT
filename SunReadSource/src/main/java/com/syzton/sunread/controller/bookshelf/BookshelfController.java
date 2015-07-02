package com.syzton.sunread.controller.bookshelf;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.comparator.BookshelfDTOComparator;
import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.model.bookshelf.Bookshelf;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.service.bookshelf.BookshelfService;
import com.syzton.sunread.service.user.UserService;

import javassist.NotFoundException;

import javax.validation.Valid;

/**
 * @Date 2015/03/12
 * @author Morgan-Leon
 */
@Controller
@RequestMapping(value = "/api")
public class BookshelfController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookshelfController.class);

    private BookshelfService service;
    private UserService	userService;

    @Autowired
    public BookshelfController(BookshelfService service,UserService userService) {
        this.service = service;
        this.userService = userService;
    }
    
    @RequestMapping(value = "/student/{studentId}/bookshelf", method = RequestMethod.POST)
    @ResponseBody
    public BookshelfDTO add(@Valid @RequestBody BookshelfDTO dto,@PathVariable("studentId") Long studentId) {
        LOGGER.debug("Adding a new book entry with information: {}", dto);

        Bookshelf added = service.add(dto,studentId);

        LOGGER.debug("Added a book entry with information: {}", added);

        return added.createDTO(added);
    }



  //Get information of a bookshelf by id  
    @RequestMapping(value = "/bookshelf/{id}", method = RequestMethod.GET)
    @ResponseBody
    public BookshelfDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding bookshelf entry with id: {}", id);
        
        Bookshelf found = service.findById(id);
        LOGGER.debug("Found bookshelf entry with information: {}", found);
        
        return found.createDTO(found);
    }
    
    //Get information of bookshelves by class id  
    @RequestMapping(value = "/class/{classId}/bookshelves", method = RequestMethod.GET)
    @ResponseBody
    public Page<BookshelfDTO> findByClassId(@PathVariable("classId") Long classId,
            								@RequestParam("page") int page,
            								@RequestParam("size") int size,
            								@RequestParam(value = "sortBy", required = false) String sortBy,
            								@RequestParam(value = "direction", required = false) String direction) throws NotFoundException {
        LOGGER.debug("Finding bookshelf entry with id: {}", classId);
        sortBy = sortBy == null ? "statistic.point" : sortBy;     
        Pageable pageable = this.getPageable(page, size,sortBy,direction);
        Page<Student> students = userService.hotReadersInClazz(classId, pageable);
        ArrayList<BookshelfDTO> bookshelves = new ArrayList<BookshelfDTO>();
        for (int i = 0; i < students.getNumberOfElements(); i++) {
        	Bookshelf found = service.findById(students.getContent().get(i).getId());
        	if (found == null) {
				throw new com.syzton.sunread.exception.common.NotFoundException("Not Found bookshelf with id"+students.getContent().get(i).getId());
			}
        	BookshelfDTO bookshelfDTO = found.createDTO(found);
        	bookshelves.add(bookshelfDTO);
		}
//        BookshelfDTOComparator c = new BookshelfDTOComparator(); 
//        bookshelves.sort(c);
//        bookshelves.sort(new BookshelfDTOComparator());


         Collections.sort(bookshelves,new BookshelfDTOComparator());
	
//        LOGGER.debug("Found bookshelf entry with information: {}", found);
        
        return new PageImpl<>(bookshelves,pageable,students.getTotalElements());
    }

    @RequestMapping(value = "/bookshelf/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public BookshelfDTO update(@Valid @RequestBody BookshelfDTO dto, @PathVariable("id") Long bookshelfId) throws NotFoundException {
        LOGGER.debug("Updating bookshelf with information: {}", dto);

        Bookshelf updated = service.update(dto);
        LOGGER.debug("Updated the information of a bookshelf to: {}", updated);

        return updated.createDTO(updated);
    }

}





