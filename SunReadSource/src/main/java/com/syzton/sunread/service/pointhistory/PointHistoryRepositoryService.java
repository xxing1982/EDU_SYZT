package com.syzton.sunread.service.pointhistory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.controller.util.SecurityContextUtil;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.model.pointhistory.PointHistory.PointType;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.model.user.UserStatistic;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.pointhistory.PointHistoryRepository;
import com.syzton.sunread.repository.user.StudentRepository;


/**
 * @author chenty
 *
 */
@Service
public class PointHistoryRepositoryService implements PointHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointHistoryRepositoryService.class);
    private PointHistoryRepository repository;

    private ClazzRepository clazzRepository;

    @Autowired
    public PointHistoryRepositoryService(PointHistoryRepository repository,ClazzRepository clazzRepository) {
        this.repository = repository;
        this.clazzRepository = clazzRepository;
    }

    private StudentRepository studentRepository;

    @Autowired
    public void StudentRepositoryService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private SecurityContextUtil securityContextUtil;
    
    @Autowired
    public void setSecurityContextUtil(SecurityContextUtil securityContextUtil) {
        this.securityContextUtil = securityContextUtil;
    }
    
    @Override
    public PointHistory add(PointHistory add) {
   	 
        // Get the student entity
        Student student = studentRepository.findOne(add.getStudentId());
        
        // Update statistics
        Clazz clazz = clazzRepository.findOne(student.getClazzId());
        UserStatistic statistic = student.getStatistic();

        if (add.getPointType() == PointType.IN) {
            statistic.setPoint(statistic.getPoint() + add.getNum());
            clazz.getClazzStatistic().setTotalPoints(clazz.getClazzStatistic().getTotalPoints() + add.getNum());
            clazz.getClazzStatistic().setAvgPoints();
        } else {
            statistic.setPoint(statistic.getPoint() - add.getNum());
            clazz.getClazzStatistic().setTotalPoints(clazz.getClazzStatistic().getTotalPoints() - add.getNum());
            clazz.getClazzStatistic().setAvgPoints();
        }
        clazzRepository.save(clazz);
        
        
        // Update student pointhistory
        switch(add.getPointFrom()){
        	case FROM_TEACHER: 
        		
        		// Get the teacher entity
                User teacher = securityContextUtil.getUser();
                
                // Save the pointhistory entity
                add.setFromId(teacher.getId());
                break;
        	case FROM_BOOK: 
                break;
        	case FROM_NOTE: 
                break;
        	case FROM_VERIFY_TEST: 
                break;                
        	default:
        		break; 
        }
        return repository.save(add);
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public PointHistory deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a pointHistory entry with id: {}", id);

        PointHistory deleted = findById(id);
        LOGGER.debug("Deleting pointHistory entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PointHistory> findAll() {
        LOGGER.debug("Finding all pointHistory entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public PointHistory findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a pointHistory entry with id: {}", id);

        PointHistory found = repository.findOne(id);
        LOGGER.debug("Found pointHistory entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No pointHistory entry found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public PointHistory update(PointHistory update) throws NotFoundException {
        LOGGER.debug("Updating contact with information: {}", update);

        PointHistory model = findById(update.getId());
        LOGGER.debug("Found a pointHistory entry: {}", model);
        model.setPointType(update.getPointType());
        model.setPointFrom(update.getPointFrom());        
        return model;
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
	@Override
	public List<PointHistory> findByStudentId(Long studentId) {
        LOGGER.debug("Finding all pointHistory entries by user id");
        Student student = studentRepository.findOne(studentId);
        return repository.findByStudent(student);
	}
}
