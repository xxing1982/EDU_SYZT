package com.syzton.sunread.controller.note;

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

import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.note.CommentDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.note.Comment;
import com.syzton.sunread.service.note.CommentService;

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

    @RequestMapping(value = "/api/notes/{noteId}/comments", method = RequestMethod.POST)
    @ResponseBody
    public CommentDTO add(@Valid @RequestBody CommentDTO dto, @PathVariable("noteId") Long noteId) {
        LOGGER.debug("Adding a new comment entry with information: {}", dto);

        Comment added = service.add(dto, noteId);

        LOGGER.debug("Added a comment entry with information: {}", added);

       return added.createDTO(added);
    }

    @RequestMapping(value = "/api/comments/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CommentDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a comment entry with id: {}", id);

        Comment deleted = service.deleteById(id);
        LOGGER.debug("Deleted comment entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/comments/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommentDTO update(@Valid @RequestBody CommentDTO dto, @PathVariable("id") Long commentId) throws NotFoundException {
        LOGGER.debug("Updating a comment entry with information: {}", dto);

        Comment updated = service.update(dto);
        LOGGER.debug("Updated the information of a comment entry to: {}", updated);

        return updated.createDTO(updated);
    }

    @RequestMapping(value = "/api/notes/{noteId}/comments", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Comment> findNotesByBookId(@PathVariable("noteId") long noteId,
										    	   @RequestParam("page") int page,
										           @RequestParam("size") int size,
										           @RequestParam("sortBy") String sortBy) {
		sortBy = sortBy == null ? "id" : sortBy;
		
		Pageable pageable = new PageRequest(
				page, size, new Sort(sortBy)
		);
		
        Page<Comment> commentPage = service.findByNoteId(pageable, noteId);

        return new PageResource<>(commentPage, "page", "size");
    }
}
