package com.syzton.sunread.service.note;

import java.util.List;

import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.exception.note.NoteNotFoundException;
import com.syzton.sunread.model.note.Note;

/**
 * @author chenty
 *
 */
public interface NoteService {

    /**
     * Adds a new note entry.
     * @param added The information of the added note entry.
     * @return  The added note entry.
     */
    public Note add(NoteDTO added);

    /**
     * Deletes a note entry.
     * @param id    The id of the deleted note entry.
     * @return  The deleted note entry.
     * @throws com.syzton.sunread.todo.exception.BookNoteNotFoundException    if no note entry is found with the given id.
     */
    public Note deleteById(Long id) throws NoteNotFoundException;

    /**
     * Returns a list of note entries.
     * @return
     */
    public List<Note> findAll();

    /**
     * Finds a note entry.
     * @param id    The id of the wanted note entry.
     * @return  The found note entry.
     * @throws NoteNotFoundException    if no note entry is found with the given id.
     */
    public Note findById(Long id) throws NoteNotFoundException;

    /**
     * Updates the information of a note entry.
     * @param updated   The information of the updated note entry.
     * @return  The updated note entry.
     * @throws NoteNotFoundException    If no note entry is found with the given id.
     */
    public Note update(NoteDTO updated) throws NoteNotFoundException;


}
