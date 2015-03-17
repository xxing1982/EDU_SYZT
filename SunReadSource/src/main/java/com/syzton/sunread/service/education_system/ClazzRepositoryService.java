package com.syzton.sunread.service.education_system;

import com.syzton.sunread.exception.coinhistory.CoinHistoryNotFoundException;
import com.syzton.sunread.model.education_system.Clazz;
import com.syzton.sunread.repository.education_system.ClazzRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public class ClazzRepositoryService implements ClazzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzRepositoryService.class);
    private ClazzRepository repository;

    @Autowired
    public ClazzRepositoryService(ClazzRepository repository) {
        this.repository = repository;
    }

    @Override
    public Clazz add(Clazz add) {

        LOGGER.debug("Adding a new clazz entry with information: {}", add);
        return repository.save(add);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Clazz deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a clazz entry with id: {}", id);

        Clazz deleted = findById(id);
        LOGGER.debug("Deleting clazz entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Clazz update(Clazz updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        Clazz model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Clazz findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a clazz entry with id: {}", id);

        Clazz found = repository.findOne(id);
        LOGGER.debug("Found clazz entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No clazz entry found with id: " + id);
        }

        return found;
    }



    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<Clazz> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all clazz entries");
        return (Page<Clazz>) repository.findAll();
    }
}
