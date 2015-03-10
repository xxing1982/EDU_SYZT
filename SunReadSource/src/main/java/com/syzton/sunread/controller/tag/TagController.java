package com.syzton.sunread.controller.tag;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.dto.tag.TagDTO;
import com.syzton.sunread.exception.tag.TagNotFoundException;
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

    @RequestMapping(value = "/api/tag", method = RequestMethod.POST)
    @ResponseBody
    public TagDTO add(@Valid @RequestBody TagDTO dto) {
        LOGGER.debug("Adding a new tag entry with information: {}", dto);

        Tag added = service.add(dto);

        LOGGER.debug("Added a tag entry with information: {}", added);

       return createDTO(added);
    }

    @RequestMapping(value = "/api/tag/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public TagDTO deleteById(@PathVariable("id") Long id) throws TagNotFoundException {
        LOGGER.debug("Deleting a tag entry with id: {}", id);

        Tag deleted = service.deleteById(id);
        LOGGER.debug("Deleted tag entry with information: {}", deleted);

        return createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/tag", method = RequestMethod.GET)
    @ResponseBody
    public List<TagDTO> findAll() {
        LOGGER.debug("Finding all tag entries.");

        List<Tag> models = service.findAll();
        LOGGER.debug("Found {} tag entries.", models.size());

        return createDTOs(models);
    }

    private List<TagDTO> createDTOs(List<Tag> models) {
        List<TagDTO> dtos = new ArrayList<TagDTO>();

        for (Tag model: models) {
            dtos.add(createDTO(model));
        }

        return dtos;
    }
    
    @RequestMapping(value = "/api/tag/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public TagDTO update(@Valid @RequestBody TagDTO dto, @PathVariable("id") Long tagId) throws TagNotFoundException {
        LOGGER.debug("Updating a tag entry with information: {}", dto);

        Tag updated = service.update(dto);
        LOGGER.debug("Updated the information of a tag entry to: {}", updated);

        return createDTO(updated);
    }
    
    private TagDTO createDTO(Tag model) {
        TagDTO dto = new TagDTO();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setValue(model.getValue());

        return dto;
    }
}
