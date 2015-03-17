package com.syzton.sunread.service.education_system;

import com.syzton.sunread.exception.coinhistory.CoinHistoryNotFoundException;
import com.syzton.sunread.model.education_system.Grade;
import com.syzton.sunread.repository.education_system.GradeRepository;
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
public class GradeRepositoryService implements GradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GradeRepositoryService.class);
    private GradeRepository repository;

    @Autowired
    public GradeRepositoryService(GradeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Grade add(Grade add) {

        LOGGER.debug("Adding a new clazz entry with information: {}", add);
        return repository.save(add);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Grade deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a clazz entry with id: {}", id);

        Grade deleted = findById(id);
        LOGGER.debug("Deleting clazz entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Grade update(Grade updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        Grade model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Grade findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a clazz entry with id: {}", id);

        Grade found = repository.findOne(id);
        LOGGER.debug("Found clazz entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No clazz entry found with id: " + id);
        }

        return found;
    }



    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<Grade> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all clazz entries");
        return (Page<Grade>) repository.findAll();
    }
}
