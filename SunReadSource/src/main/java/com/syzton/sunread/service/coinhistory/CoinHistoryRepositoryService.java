package com.syzton.sunread.service.coinhistory;

import java.util.List;

import com.syzton.sunread.controller.util.SecurityContextUtil;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.coinhistory.CoinHistory.CoinType;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.model.user.UserStatistic;
import com.syzton.sunread.repository.coinhistory.CoinHistoryRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    
    private SecurityContextUtil securityContextUtil;
    
    @Autowired
    public void setSecurityContextUtil(SecurityContextUtil securityContextUtil) {
        this.securityContextUtil = securityContextUtil;
    }

    @Override
    public CoinHistory add(CoinHistory add) {
    	 
        // Get the student entity
        Student student = studentRepository.findOne(add.getStudentId());
        
        // Calculate the statistic
        UserStatistic statistic = student.getStatistic();
        if (add.getCoinType() == CoinType.IN) {
        	statistic.setCoin( statistic.getCoin() + add.getNum() );
        } else {
        	statistic.setCoin( statistic.getCoin() - add.getNum() );
        }
        studentRepository.save(student);
        

        switch(add.getCoinFrom()){
        	case FROM_TEACHER: 
        		
        		// Get the teacher entity
                User teacher = securityContextUtil.getUser();
                
                // Save the coinhistory entity
                add.setFromId(teacher.getId());
                break;
        	case FROM_BOOK: 
        		
                // Save the coinhistory entity
//                add.setFromId(teacher.getId());
                break;
        	case FROM_NOTE: 
        		
                // Save the coinhistory entity
//                add.setFromId(teacher.getId());
                break;
        	case FROM_VERIFY_TEST: 
        		
                // Save the coinhistory entity
//                add.setFromId(teacher.getId());
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
}
