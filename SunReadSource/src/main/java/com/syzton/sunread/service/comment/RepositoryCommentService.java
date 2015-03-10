package com.syzton.sunread.service.comment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.comment.CommentDTO;
import com.syzton.sunread.exception.comment.CommentNotFoundException;
import com.syzton.sunread.model.comment.Comment;
import com.syzton.sunread.repository.comment.CommentRepository;


/**
 * @author chenty
 *
 */
@Service
public class RepositoryCommentService implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCommentService.class);
    private CommentRepository repository;

    @Autowired
    public RepositoryCommentService(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment add(CommentDTO added) {
        LOGGER.debug("Adding a new comment entry with information: {}", added);

        Comment commentModel = Comment.getBuilder(added.getContent())
                .build();
        
        return repository.save(commentModel);
    }
    
    @Transactional(rollbackFor = {CommentNotFoundException.class})
    @Override
    public Comment deleteById(Long id) throws CommentNotFoundException {
        LOGGER.debug("Deleting a comment entry with id: {}", id);

        Comment deleted = findById(id);
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

    @Transactional(readOnly = true, rollbackFor = {CommentNotFoundException.class})
    @Override
    public Comment findById(Long id) throws CommentNotFoundException {
        LOGGER.debug("Finding a comment entry with id: {}", id);

        Comment found = repository.findOne(id);
        LOGGER.debug("Found comment entry: {}", found);

        if (found == null) {
            throw new CommentNotFoundException("No comment entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {CommentNotFoundException.class})
    @Override
    public Comment update(CommentDTO updated) throws CommentNotFoundException {
        LOGGER.debug("Updating contact with information: {}", updated);

        Comment model = findById(updated.getId());
        LOGGER.debug("Found a comment entry: {}", model);

        model.update(updated.getContent());

        return model;
    }
}
