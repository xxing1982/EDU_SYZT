package com.syzton.sunread.service.tag;

import java.util.List;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.repository.tag.TagRepository;
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
public class TagRepositoryService implements TagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagRepositoryService.class);
    private TagRepository repository;

    @Autowired
    public TagRepositoryService(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tag add(Tag added) {
        LOGGER.debug("Adding a new tag entry with information: {}", added);
        return repository.save(added);
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Tag deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a tag entry with id: {}", id);

        Tag deleted = findById(id);
        LOGGER.debug("Deleting tag entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Tag> findAll() {
        LOGGER.debug("Finding all tag entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Tag findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a tag entry with id: {}", id);

        Tag found = repository.findOne(id);
        LOGGER.debug("Found tag entry: {}", found);

        if (found == null) {
            throw new NotFoundException("Tag entry with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Tag update(Tag updated) throws NotFoundException {
        LOGGER.debug("Updating contact with information: {}", updated);

        Tag model = findById(updated.getId());
        LOGGER.debug("Found a tag entry: {}", model);

        model.update(updated.getType(), updated.getName());

        return model;
    }
}
