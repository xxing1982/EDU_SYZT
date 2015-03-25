package com.syzton.sunread.service.note;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.exception.common.NotFoundException;
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
    public Note add(NoteDTO added, Long bookId);

    /**
     * Deletes a note entry.
     * @param id    The id of the deleted note entry.
     * @return  The deleted note entry.
     * @throws com.syzton.sunread.todo.exception.BookNotFoundException    if no note entry is found with the given id.
     */
    public Note deleteById(Long id) throws NotFoundException;

    /**
     * Returns a list of note entries.
     * @return
     */
    public List<Note> findAll();

    /**
     * Finds a note entry.
     * @param id    The id of the wanted note entry.
     * @return  The found note entry.
     * @throws NotFoundException    if no note entry is found with the given id.
     */
    public Note findById(Long id) throws NotFoundException;

    /**
     * Updates the information of a note entry.
     * @param updated   The information of the updated note entry.
     * @return  The updated note entry.
     * @throws NotFoundException    If no note entry is found with the given id.
     */
    public Note update(NoteDTO updated) throws NotFoundException;

	Page<Note> findByBookId(Pageable pageable, long bookId);

	Page<Note> findByUserId(Pageable pageable, long userId);

}
