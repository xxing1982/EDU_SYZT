package com.syzton.sunread.service.semester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.assembler.semester.SemesterAssembler;
import com.syzton.sunread.dto.semester.SemesterDTO;
import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.repository.SemesterRepository;

/*
 * @Date 2015-3-22
 * @Author Morgan-Leon 
 */
@Service
public class SemesterRepositoryService implements SemesterService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SemesterRepositoryService.class);

    private SemesterRepository semesterRepo;
    
    @Autowired
    public SemesterRepositoryService(SemesterRepository repository) {
		// TODO Auto-generated constructor stub
    	this.semesterRepo = repository;
	}
    
    @Transactional(rollbackFor = {DuplicateException.class})
	@Override
	public SemesterDTO add(SemesterDTO added) {
        LOGGER.debug("Adding a new Semester with information: {}", added);
        
        Semester exits = semesterRepo.findOne(added.getId());        
        if(exits != null){
            throw new DuplicateException("Semester with id: "+added.getId()+" is already exits..");
        }
        
        SemesterAssembler assembler = new SemesterAssembler();
        
        Semester model = assembler.fromDTOtoModel(added);
        model = semesterRepo.save(model);
        added.setId(model.getId());
        return added;
	}

    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public Semester deleteById(long id) {
        LOGGER.debug("Deleting a Semester with id: {}", id);

        Semester deleted = findOne(id);
        if(deleted == null)
            throw new NotFoundException("No Semester found with id: " + id);
        
        LOGGER.debug("Deleting semester entry: {}", deleted);
        semesterRepo.delete(deleted);
        return deleted;
	}

    @Transactional(rollbackFor = {NotFoundException.class})
	@Override
	public SemesterDTO update(SemesterDTO updated) {
        LOGGER.debug("Updating contact with information: {}", updated);

        Semester model = findOne(updated.getId());
        if(model == null)
            throw new NotFoundException("No Semester found with id: " + updated.getId());
        LOGGER.debug("Found a note entry: {}", model);
        
        SemesterAssembler assembler = new SemesterAssembler();     
        Semester semester = assembler.fromDTOtoModel(updated);
        updated.setId(semester.getId());
        return updated;
	}

	@Override
	public Semester findOne(Long id) {
        LOGGER.debug("Finding a Semester with id: {}", id);

        Semester found = semesterRepo.findOne(id);
        LOGGER.debug("Found semester entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No Semester found with id: " + id);
        }
        return found;
	}

	@Override
	public Page<Semester> findAll(Pageable pageable) {
        LOGGER.debug("Finding all semester entries");
        Page<Semester> eduPages = semesterRepo.findAll(pageable);
        if (eduPages == null) {
            throw new NotFoundException("No Semester found");
        }
        return eduPages;
    }
	

}
