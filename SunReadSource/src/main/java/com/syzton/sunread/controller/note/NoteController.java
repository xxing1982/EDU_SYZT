package com.syzton.sunread.controller.note;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.exception.note.NoteNotFoundException;
import com.syzton.sunread.model.note.Note;
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

    @RequestMapping(value = "/api/note", method = RequestMethod.POST)
    @ResponseBody
    public NoteDTO add(@Valid @RequestBody NoteDTO dto) {
        LOGGER.debug("Adding a new note entry with information: {}", dto);

        Note added = service.add(dto);

        LOGGER.debug("Added a note entry with information: {}", added);

       return createDTO(added);
    }

    @RequestMapping(value = "/api/note/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public NoteDTO deleteById(@PathVariable("id") Long id) throws NoteNotFoundException {
        LOGGER.debug("Deleting a note entry with id: {}", id);

        Note deleted = service.deleteById(id);
        LOGGER.debug("Deleted note entry with information: {}", deleted);

        return createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/note", method = RequestMethod.GET)
    @ResponseBody
    public List<NoteDTO> findAll() {
        LOGGER.debug("Finding all note entries.");

        List<Note> models = service.findAll();
        LOGGER.debug("Found {} note entries.", models.size());

        return createDTOs(models);
    }

    private List<NoteDTO> createDTOs(List<Note> models) {
        List<NoteDTO> dtos = new ArrayList<NoteDTO>();

        for (Note model: models) {
            dtos.add(createDTO(model));
        }

        return dtos;
    }
    
    @RequestMapping(value = "/api/note/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public NoteDTO update(@Valid @RequestBody NoteDTO dto, @PathVariable("id") Long noteId) throws NoteNotFoundException {
        LOGGER.debug("Updating a note entry with information: {}", dto);

        Note updated = service.update(dto);
        LOGGER.debug("Updated the information of a note entry to: {}", updated);

        return createDTO(updated);
    }
    
    private NoteDTO createDTO(Note model) {
        NoteDTO dto = new NoteDTO();

        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setContent(model.getContent());
        dto.setImage(model.getImage());

        return dto;
    }
}
