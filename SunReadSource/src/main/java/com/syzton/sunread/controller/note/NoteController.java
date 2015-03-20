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
import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.tag.BookTag;
import com.syzton.sunread.service.note.NoteService;

import javax.validation.Valid;


/**
 * @author chenty
 *
 */
@Controller
public class NoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

    private NoteService service;

    @Autowired
    public NoteController(NoteService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/books/{bookId}/notes", method = RequestMethod.POST)
    @ResponseBody
    public NoteDTO add(@Valid @RequestBody NoteDTO dto, @PathVariable("bookId") Long bookId) {
        LOGGER.debug("Adding a new note entry with information: {}", dto);

        Note added = service.add(dto, bookId);

        LOGGER.debug("Added a note entry with information: {}", added);

       return added.createDTO(added);
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public NoteDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a note entry with id: {}", id);

        Note deleted = service.deleteById(id);
        LOGGER.debug("Deleted note entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/notes", method = RequestMethod.GET)
    @ResponseBody
    public List<NoteDTO> findAll() {
        LOGGER.debug("Finding all note entries.");

        List<Note> models = service.findAll();
        LOGGER.debug("Found {} note entries.", models.size());

        return createDTOs(models);
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public NoteDTO update(@Valid @RequestBody NoteDTO dto, @PathVariable("id") Long noteId) throws NotFoundException {
        LOGGER.debug("Updating a note entry with information: {}", dto);

        Note updated = service.update(dto);
        LOGGER.debug("Updated the information of a note entry to: {}", updated);

        return updated.createDTO(updated);
    }
    
    @RequestMapping(value = "/api/books/{bookId}/notes", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Note> findNotesByBookId(@PathVariable("bookId") long bookId,
										    	@RequestParam("page") int page,
										        @RequestParam("size") int size,
										        @RequestParam("sortBy") String sortBy) {
		sortBy = sortBy == null ? "id" : sortBy;
		
		Pageable pageable = new PageRequest(
				page, size, new Sort(sortBy)
		);
		
        Page<Note> notePage = service.findByBookId(pageable, bookId);

        return new PageResource<>(notePage, "page", "size");
    }
    
    @RequestMapping(value = "/api/users/{userId}/notes", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Note> findNotesByUserId(@PathVariable("userId") long userId,
										    	@RequestParam("page") int page,
										        @RequestParam("size") int size,
										        @RequestParam("sortBy") String sortBy) {
		sortBy = sortBy == null ? "id" : sortBy;
		
		Pageable pageable = new PageRequest(
				page, size, new Sort(sortBy)
		);
		
        Page<Note> notePage = service.findByUserId(pageable, userId);

        return new PageResource<>(notePage, "page", "size");
    }
    
    private List<NoteDTO> createDTOs(List<Note> models) {
        List<NoteDTO> dtos = new ArrayList<NoteDTO>();

        for (Note model: models) {
            dtos.add(model.createDTO(model));
        }

        return dtos;
    }
}
