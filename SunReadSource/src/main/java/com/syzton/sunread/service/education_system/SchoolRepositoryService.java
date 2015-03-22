package com.syzton.sunread.service.education_system;

import com.syzton.sunread.dto.education_system.SchoolDTO;
import com.syzton.sunread.model.education_system.EduGroup;
import com.syzton.sunread.model.education_system.School;
import com.syzton.sunread.repository.education_system.EduGroupRepository;
import com.syzton.sunread.repository.education_system.SchoolRepository;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Service
public class SchoolRepositoryService implements SchoolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolRepositoryService.class);
    private SchoolRepository repository;
    private EduGroupRepository eduRepository;

    @Autowired
    public SchoolRepositoryService(SchoolRepository repository) {
        this.repository = repository;
    }

    @Override
    public School add(SchoolDTO add, Long eduId) {

        LOGGER.debug("Adding a new school entry with information: {}", add);
        EduGroup eduGroup =  eduRepository.findOne(eduId);
        School school = School.getBuilder(add.getName(),eduGroup)
        		.description(add.getDescription()).build();
        return repository.save(school);

    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public School deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a school entry with id: {}", id);

        School deleted = findById(id);
        LOGGER.debug("Deleting school entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public School update(SchoolDTO updated)throws  NotFoundException{
        LOGGER.debug("Updating contact with information: {}", updated);

        School model = findById(updated.getId());
        LOGGER.debug("Found a note entry: {}", model);

        model.update(updated.getName());
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public School findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a school entry with id: {}", id);

        School found = repository.findOne(id);
        LOGGER.debug("Found school entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No school entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<School> findAll(Pageable pageable) throws NotFoundException {
        LOGGER.debug("Finding all school entries");
        return  repository.findAll(pageable);
    }
}
