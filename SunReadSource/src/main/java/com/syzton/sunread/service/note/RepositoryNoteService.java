package com.syzton.sunread.service.note;

import java.util.List;

import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.exception.note.NoteNotFoundException;
import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.repository.note.NoteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author chenty
 *
 */
@Service
public class RepositoryNoteService implements NoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryNoteService.class);
    private NoteRepository repository;

    @Autowired
    public RepositoryNoteService(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Note add(NoteDTO added) {
        LOGGER.debug("Adding a new note entry with information: {}", added);

        Note noteModel = Note.getBuilder(added.getTitle(), added.getContent())
        		.image(added.getImage())
                .build();
        
        return repository.save(noteModel);
    }
    
    @Transactional(rollbackFor = {NoteNotFoundException.class})
    @Override
    public Note deleteById(Long id) throws NoteNotFoundException {
        LOGGER.debug("Deleting a note entry with id: {}", id);

        Note deleted = findById(id);
        LOGGER.debug("Deleting note entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Note> findAll() {
        LOGGER.debug("Finding all note entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {NoteNotFoundException.class})
    @Override
    public Note findById(Long id) throws NoteNotFoundException {
        LOGGER.debug("Finding a note entry with id: {}", id);

        Note found = repository.findOne(id);
        LOGGER.debug("Found note entry: {}", found);

        if (found == null) {
            throw new NoteNotFoundException("No note entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NoteNotFoundException.class})
    @Override
    public Note update(NoteDTO updated) throws NoteNotFoundException {
        LOGGER.debug("Updating contact with information: {}", updated);

        Note model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getTitle(), updated.getContent());

        return model;
    }
}
