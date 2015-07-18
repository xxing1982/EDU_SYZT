package com.syzton.sunread.service.note;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.controller.util.SecurityContextUtil;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.note.Comment;
import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.note.CommentRepository;
import com.syzton.sunread.repository.note.NoteRepository;


/**
 * @author chenty
 *
 */
@Service
public class CommentRepositoryService implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentRepositoryService.class);
    private CommentRepository repository;

    @Autowired
    public CommentRepositoryService(CommentRepository repository) {
        this.repository = repository;
    }

    private NoteRepository noteRepository;

    @Autowired
    public void NoteRepositoryService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    
    private SecurityContextUtil securityContextUtil;
    
    @Autowired
    public void setSecurityContextUtil(SecurityContextUtil securityContextUtil) {
        this.securityContextUtil = securityContextUtil;
    }
    
    @Override
    public Comment add(Comment added, Long noteId) {
        LOGGER.debug("Adding a new comment entry with information: {}", added);

        Note note = noteRepository.findOne(noteId);
        note.setCommentCount( note.getCommentCount() + 1 );
        User user = securityContextUtil.getUser();
        Comment comment = new Comment();
        comment.setContent(added.getContent());
        comment.setNote(note);
        comment.setUser(user);        
        return repository.save(comment);
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Comment deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a comment entry with id: {}", id);

        Comment deleted = findById(id);
        deleted.getNote().setCommentCount( deleted.getNote().getCommentCount() - 1 );
        LOGGER.debug("Deleting comment entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        LOGGER.debug("Finding all comment entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Comment findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a comment entry with id: {}", id);

        Comment found = repository.findOne(id);
        LOGGER.debug("Found comment entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No comment entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Comment update(Comment updated) throws NotFoundException {
        LOGGER.debug("Updating contact with information: {}", updated);

        Comment model = findById(updated.getId());
        LOGGER.debug("Found a comment entry: {}", model);

        model.setContent(updated.getContent());

        return model;
    }

	@Override
	public Page<Comment> findByNoteId(Pageable pageable, long noteId) {

        Note note = noteRepository.findOne(noteId);
        Page<Comment> commentPage = repository.findByNote(note, pageable);
        
        return commentPage;
	}
}
