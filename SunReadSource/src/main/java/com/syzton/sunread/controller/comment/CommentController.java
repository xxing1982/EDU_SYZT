package com.syzton.sunread.controller.comment;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.dto.comment.CommentDTO;
import com.syzton.sunread.exception.comment.CommentNotFoundException;
import com.syzton.sunread.model.comment.Comment;
import com.syzton.sunread.service.comment.CommentService;

import javax.validation.Valid;


/**
 * @author chenty
 *
 */
@Controller
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/comment", method = RequestMethod.POST)
    @ResponseBody
    public CommentDTO add(@Valid @RequestBody CommentDTO dto) {
        LOGGER.debug("Adding a new comment entry with information: {}", dto);

        Comment added = service.add(dto);

        LOGGER.debug("Added a comment entry with information: {}", added);

       return createDTO(added);
    }

    @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommentDTO deleteById(@PathVariable("id") Long id) throws CommentNotFoundException {
        LOGGER.debug("Deleting a comment entry with id: {}", id);

        Comment deleted = service.deleteById(id);
        LOGGER.debug("Deleted comment entry with information: {}", deleted);

        return createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/comment", method = RequestMethod.GET)
    @ResponseBody
    public List<CommentDTO> findAll() {
        LOGGER.debug("Finding all comment entries.");

        List<Comment> models = service.findAll();
        LOGGER.debug("Found {} comment entries.", models.size());

        return createDTOs(models);
    }

    private List<CommentDTO> createDTOs(List<Comment> models) {
        List<CommentDTO> dtos = new ArrayList<CommentDTO>();

        for (Comment model: models) {
            dtos.add(createDTO(model));
        }

        return dtos;
    }
    
    @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommentDTO update(@Valid @RequestBody CommentDTO dto, @PathVariable("id") Long commentId) throws CommentNotFoundException {
        LOGGER.debug("Updating a comment entry with information: {}", dto);

        Comment updated = service.update(dto);
        LOGGER.debug("Updated the information of a comment entry to: {}", updated);

        return createDTO(updated);
    }
    
    private CommentDTO createDTO(Comment model) {
        CommentDTO dto = new CommentDTO();

        dto.setId(model.getId());
        dto.setContent(model.getContent());
        return dto;
    }
}
