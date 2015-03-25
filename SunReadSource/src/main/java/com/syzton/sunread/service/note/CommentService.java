package com.syzton.sunread.service.note;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.syzton.sunread.dto.note.CommentDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.note.Comment;

/**
 * @author chenty
 *
 */
public interface CommentService {

    /**
     * Adds a new comment entry.
     * @param added The information of the added comment entry.
     * @return  The added comment entry.
     */
    public Comment add(CommentDTO added, Long noteId);

    /**
     * Deletes a comment entry.
     * @param id    The id of the deleted comment entry.
     * @return  The deleted comment entry.
     * @throws com.syzton.sunread.todo.exception.BookNotFoundException    if no comment entry is found with the given id.
     */
    public Comment deleteById(Long id) throws NotFoundException;

    /**
     * Returns a list of comment entries.
     * @return
     */
    public List<Comment> findAll();

    /**
     * Finds a comment entry.
     * @param id    The id of the wanted comment entry.
     * @return  The found comment entry.
     * @throws NotFoundException    if no comment entry is found with the given id.
     */
    public Comment findById(Long id) throws NotFoundException;

    /**
     * Updates the information of a comment entry.
     * @param updated   The information of the updated comment entry.
     * @return  The updated comment entry.
     * @throws NotFoundException    If no comment entry is found with the given id.
     */
    public Comment update(CommentDTO updated) throws NotFoundException;

	public Page<Comment> findByNoteId(Pageable pageable, long noteId);


}
