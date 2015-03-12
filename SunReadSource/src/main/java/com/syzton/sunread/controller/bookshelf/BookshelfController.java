//package com.syzton.sunread.controller.bookshelf;
//import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
//import com.syzton.sunread.exception.bookshelf.BookshelfNotFoundException;
//import com.syzton.sunread.model.bookshelf.Bookshelf;
//import com.syzton.sunread.service.bookshelf.BookshelfService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
//* @author Morgan-Leon
//*/
//@Controller
//public class BookshelfController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BookshelfController.class);
//
//    private BookshelfService service;
//
//    @Autowired
//    public BookshelfController(BookshelfService service) {
//        this.service = service;
//    }
//
//    @RequestMapping(value = "/api/bookshelf", method = RequestMethod.POST)
//    @ResponseBody
//    public BookshelfDTO add(@Valid @RequestBody BookshelfDTO dto) {
//        LOGGER.debug("Adding a new bookshelf entry with information: {}", dto);
//
//        Bookshelf added = service.add(dto);
//        LOGGER.debug("Added a to-do entry with information: {}", added);
//
//       return createDTO(added);
//    }
//
//    @RequestMapping(value = "/api/bookshelf/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public BookshelfDTO deleteById(@PathVariable("id") Long id) throws BookshelfNotFoundException {
//        LOGGER.debug("Deleting a to-do entry with id: {}", id);
//
//        Bookshelf deleted = service.deleteById(id);
//        LOGGER.debug("Deleted to-do entry with information: {}", deleted);
//
//        return createDTO(deleted);
//    }
//
////Get information of all bookshelves
//    @RequestMapping(value = "/api/bookshelf", method = RequestMethod.GET)
//    @ResponseBody
//    public List<BookshelfDTO> findAll() {
//        LOGGER.debug("Finding all bookshelf entries.");
//
//        List<Bookshelf> models = service.findAll();
//        LOGGER.debug("Found {} bookshelf entries.", models.size());
//
//        return createDTOs(models);
//    }
//
//    private List<BookshelfDTO> createDTOs(List<Bookshelf> models) {
//        List<BookshelfDTO> dtos = new ArrayList<BookshelfDTO>();
//
//        for (Bookshelf model: models) {
//            dtos.add(createDTO(model));
//        }
//
//        return dtos;
//    }
//
//  //Get information of a bookshelf by id
//    @RequestMapping(value = "/api/bookshelf/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public BookshelfDTO findById(@PathVariable("id") Long id) throws BookshelfNotFoundException {
//        LOGGER.debug("Finding bookshelf entry with id: {}", id);
//
//        Bookshelf found = service.findById(id);
//        LOGGER.debug("Found bookshelf entry with information: {}", found);
//
//
//        return createDTO(found);
//    }
//
//    @RequestMapping(value = "/api/bookshelf/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public BookshelfDTO update(@Valid @RequestBody BookshelfDTO dto, @PathVariable("id") Long bookshelfId) throws BookshelfNotFoundException {
//        LOGGER.debug("Updating a to-do entry with information: {}", dto);
//
//        Bookshelf updated = service.update(dto);
//        LOGGER.debug("Updated the information of a to-entry to: {}", updated);
//
//        return createDTO(updated);
//    }
//
//
//
//	private BookshelfDTO createDTO(Bookshelf added) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
