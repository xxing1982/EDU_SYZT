package com.syzton.sunread.controller.booktag;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.dto.booktag.BookTagDTO;
import com.syzton.sunread.exception.booktag.BookTagNotFoundException;
import com.syzton.sunread.model.booktag.BookTag;
import com.syzton.sunread.service.booktag.BookTagService;
import javax.validation.Valid;


/**
 * @author chenty
 *
 */
@Controller
public class BookTagController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookTagController.class);

    private BookTagService service;

    @Autowired
    public BookTagController(BookTagService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/booktag", method = RequestMethod.POST)
    @ResponseBody
    public BookTagDTO add(@Valid @RequestBody BookTagDTO dto) {
        LOGGER.debug("Adding a new booktag entry with information: {}", dto);

        BookTag added = service.add(dto);

        LOGGER.debug("Added a booktag entry with information: {}", added);

       return createDTO(added);
    }

    @RequestMapping(value = "/api/booktag/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookTagDTO deleteById(@PathVariable("id") Long id) throws BookTagNotFoundException {
        LOGGER.debug("Deleting a booktag entry with id: {}", id);

        BookTag deleted = service.deleteById(id);
        LOGGER.debug("Deleted booktag entry with information: {}", deleted);

        return createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/booktag", method = RequestMethod.GET)
    @ResponseBody
    public List<BookTagDTO> findAll() {
        LOGGER.debug("Finding all tag entries.");

        List<BookTag> models = service.findAll();
        LOGGER.debug("Found {} booktag entries.", models.size());

        return createDTOs(models);
    }

    private List<BookTagDTO> createDTOs(List<BookTag> models) {
        List<BookTagDTO> dtos = new ArrayList<BookTagDTO>();

        for (BookTag model: models) {
            dtos.add(createDTO(model));
        }

        return dtos;
    }    
    
    private BookTagDTO createDTO(BookTag model) {
        BookTagDTO dto = new BookTagDTO();

        dto.setId(model.getId());

        return dto;
    }
}
