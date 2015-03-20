package com.syzton.sunread.controller.tag;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.tag.TagDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Review;
import com.syzton.sunread.model.tag.BookTag;
import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.service.tag.TagService;


import javax.validation.Valid;


/**
 * @author chenty
 *
 */
@Controller
public class TagController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

    private TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/tags", method = RequestMethod.POST)
    @ResponseBody
    public TagDTO add(@Valid @RequestBody TagDTO dto) {
        LOGGER.debug("Adding a new tag entry with information: {}", dto);

        Tag added = service.add(dto);

        LOGGER.debug("Added a tag entry with information: {}", added);

       return added.createDTO(added);
    }

    @RequestMapping(value = "/api/tags/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public TagDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a tag entry with id: {}", id);

        Tag deleted = service.deleteById(id);
        LOGGER.debug("Deleted tag entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/tags", method = RequestMethod.GET)
    @ResponseBody
    public List<TagDTO> findAll() {
        LOGGER.debug("Finding all tag entries.");

        List<Tag> models = service.findAll();
        LOGGER.debug("Found {} tag entries.", models.size());

        return createDTOs(models);
    }
    
    @RequestMapping(value = "/api/tags/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public TagDTO update(@Valid @RequestBody TagDTO dto, @PathVariable("id") Long tagId) throws NotFoundException {
        LOGGER.debug("Updating a tag entry with information: {}", dto);

        Tag updated = service.update(dto);
        LOGGER.debug("Updated the information of a tag entry to: {}", updated);

        return updated.createDTO(updated);
    }
    
    private List<TagDTO> createDTOs(List<Tag> models) {
        List<TagDTO> dtos = new ArrayList<TagDTO>();

        for (Tag model: models) {
            dtos.add(model.createDTO(model));
        }

        return dtos;
    }
    

}
