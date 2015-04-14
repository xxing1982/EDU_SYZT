package com.syzton.sunread.service.organization;

import java.util.List;

import com.syzton.sunread.dto.organization.ClazzDTO;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.user.StudentRepository;

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
public class ClazzRepositoryService implements ClazzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzRepositoryService.class);
    
    private ClazzRepository repository;
    
    private StudentRepository studentRepository;
    
    private CampusRepository campusRepository;

    @Autowired
    public ClazzRepositoryService(ClazzRepository repository,CampusRepository campusRepository,StudentRepository studentRepository) {
        this.repository = repository;
        this.campusRepository = campusRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Clazz add(ClazzDTO add, Long campusId) {

        LOGGER.debug("Adding a new clazz entry with information: {}", add);
        Campus campus = campusRepository.findOne(campusId);
        Clazz model = Clazz.getBuilder(add.getName(),add.getGrade(),campus)
        		.description(add.getDescription()).build();        
        return repository.save(model);

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
    public Clazz update(ClazzDTO updated)throws  NotFoundException{
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
        return repository.findAll(pageable);
    }

	@Override
	public List<Student> findAllStudentFromClazz(int clazzId) {
		return studentRepository.findByClazzId(clazzId);
	
	}
 
	@Override
	public int getAveragePointsfromClass(int clazzId) throws NotFoundException {
		List<Student> students = findAllStudentFromClazz(clazzId);
		int total = 0;
		if(students.size() == 0){
			throw new NotFoundException("Can't find class id:"+clazzId);
		}
		for(int i=0;i<students.size();i++){
			Student student = students.get(i);
			total = total + student.getStatistic().getPoint();
		}
		return total/students.size();
	}

	@Override
	public int getAverageReadingBookFromClass(int clazzId)
			throws NotFoundException {
		List<Student> students = findAllStudentFromClazz(clazzId);
		int total = 0;
		if(students.size() == 0){
			throw new NotFoundException("Can't find class id:"+clazzId);
		}
		for(int i=0;i<students.size();i++){
			Student student = students.get(i);
			total = total + student.getStatistic().getReadNum();
		}
		return total/students.size();
	}
}
