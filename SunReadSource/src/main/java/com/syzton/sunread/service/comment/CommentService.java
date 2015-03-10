package com.syzton.sunread.service.comment;

import java.util.List;

import com.syzton.sunread.dto.comment.CommentDTO;
import com.syzton.sunread.exception.comment.CommentNotFoundException;
import com.syzton.sunread.model.comment.Comment;

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
    public Comment add(CommentDTO added);

    /**
     * Deletes a comment entry.
     * @param id    The id of the deleted comment entry.
     * @return  The deleted comment entry.
     * @throws com.syzton.sunread.todo.exception.BookCommentNotFoundException    if no comment entry is found with the given id.
     */
    public Comment deleteById(Long id) throws CommentNotFoundException;

    /**
     * Returns a list of comment entries.
     * @return
     */
    public List<Comment> findAll();

    /**
     * Finds a comment entry.
     * @param id    The id of the wanted comment entry.
     * @return  The found comment entry.
     * @throws CommentNotFoundException    if no comment entry is found with the given id.
     */
    public Comment findById(Long id) throws CommentNotFoundException;

    /**
     * Updates the information of a comment entry.
     * @param updated   The information of the updated comment entry.
     * @return  The updated comment entry.
     * @throws CommentNotFoundException    If no comment entry is found with the given id.
     */
    public Comment update(CommentDTO updated) throws CommentNotFoundException;


}
