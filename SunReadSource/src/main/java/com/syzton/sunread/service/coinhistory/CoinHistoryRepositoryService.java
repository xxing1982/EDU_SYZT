package com.syzton.sunread.service.coinhistory;

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
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.coinhistory.CoinHistory.CoinType;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.model.user.UserStatistic;
import com.syzton.sunread.repository.SemesterRepository;
import com.syzton.sunread.repository.coinhistory.CoinHistoryRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.repository.user.StudentRepository;


/**
 * @author chenty
 *
 */
@Service
public class CoinHistoryRepositoryService implements CoinHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinHistoryRepositoryService.class);
    private CoinHistoryRepository repository;
    
    @Autowired
    public CoinHistoryRepositoryService(CoinHistoryRepository repository) {
        this.repository = repository;
    }

    
    private StudentRepository studentRepository;

    @Autowired
    public void StudentRepositoryService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    private ClazzRepository clazzRepository;

    @Autowired
    public void ClazzRepositoryService(ClazzRepository clazzRepository) {
        this.clazzRepository = clazzRepository;
    }

    private SemesterRepository semesterRepository;

    @Autowired
    public void SemesterRepositoryService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }
    
    private SecurityContextUtil securityContextUtil;
    
    @Autowired
    public void setSecurityContextUtil(SecurityContextUtil securityContextUtil) {
        this.securityContextUtil = securityContextUtil;
    }

    @Override
    public CoinHistory add(CoinHistory add) {
    	 
        // Get the student entity
        Student student = studentRepository.findOne(add.getStudentId());
        
        // Update student statistic
        UserStatistic statistic = student.getStatistic();
        
        // Update clazz statistic
        Clazz clazz = clazzRepository.findOne(student.getClazzId());

        if (clazz != null) {
	        if (add.getCoinType() == CoinType.IN) {
	            statistic.setCoin(statistic.getCoin() + add.getNum());
	            clazz.getClazzStatistic().setTotalCoin(clazz.getClazzStatistic().getTotalCoin() + add.getNum());
	            clazz.getClazzStatistic().setAvgCoin();
	        } else {
	            statistic.setCoin(statistic.getCoin() - add.getNum());
	            clazz.getClazzStatistic().setTotalCoin(clazz.getClazzStatistic().getTotalCoin() - add.getNum());
	            clazz.getClazzStatistic().setAvgCoin();
	        }
	        clazzRepository.save(clazz);
    	}
        
        
        // Update student coinhistory
        switch(add.getCoinFrom()){
        	case FROM_TEACHER: 
        		
        		// Get the teacher entity
                User teacher = securityContextUtil.getUser();
                
                // Save the coinhistory entity
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
    public CoinHistory deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a coinHistory entry with id: {}", id);

        CoinHistory deleted = findById(id);
        LOGGER.debug("Deleting coinHistory entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CoinHistory> findAll() {
        LOGGER.debug("Finding all coinHistory entries");
        return repository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public CoinHistory findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a coinHistory entry with id: {}", id);

        CoinHistory found = repository.findOne(id);
        LOGGER.debug("Found coinHistory entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No coinHistory entry found with id: " + id);
        }

        return found;
    }
    
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Page<CoinHistory> findByTeacherId(Pageable pageable, Long teacherId) throws NotFoundException {
        Page<CoinHistory> coinhistoryPage = repository.findByFromIdAndCoinFrom(teacherId, CoinHistory.CoinFrom.FROM_TEACHER, pageable);
        return coinhistoryPage;
    }    
    
    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public Page<CoinHistory> findByClassId(Pageable pageable, Long classId) throws NotFoundException {
        Page<CoinHistory> coinhistoryPage = repository.findByClassIdAndCoinFrom(classId, CoinHistory.CoinFrom.FROM_TEACHER, pageable);
        return coinhistoryPage;
    }
    
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public CoinHistory update(CoinHistory update) throws NotFoundException {
        LOGGER.debug("Updating contact with information: {}", update);

        CoinHistory model = findById(update.getId());
        LOGGER.debug("Found a coinHistory entry: {}", model);
        model.setCoinType(update.getCoinType());
        model.setCoinFrom(update.getCoinFrom());        
        return model;
    }

	@Override
	public List<CoinHistory> findBySemesterId(long semesterId) {
		Semester semester = semesterRepository.findOne(semesterId);
		List<CoinHistory> coinHistories = repository.findBySemester(semester.getStartTime(), semester.getEndTime());
		return coinHistories;
	}
}
