package com.syzton.sunread.controller.coinhistory;

import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.service.coinhistory.CoinHistoryService;
import com.syzton.sunread.service.semester.SemesterService;
import com.syzton.sunread.service.user.UserService;


/**
 * @author chenty
 *
 */
@Controller
@RequestMapping(value = "/api")
public class CoinHistoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinHistoryController.class);

    private CoinHistoryService service;

	private UserService userService;
	
	private StudentRepository studentRepository;

    @Autowired
    public CoinHistoryController(CoinHistoryService service, UserService userService, StudentRepository studentRepository) {
        this.service = service;
        this.userService = userService;
        this.studentRepository = studentRepository;
    }
    
    private SemesterService semesterService;
    
    @Autowired
    public void SemesterServiceController(SemesterService semesterService){
    	this.semesterService = semesterService;    	
    }
    
    @RequestMapping(value = "/students/{studentId}/coinhistories", method = RequestMethod.POST)
    @ResponseBody
    public CoinHistory add(@Valid @RequestBody CoinHistory add, @PathVariable("studentId") long studentId) {
        LOGGER.debug("Adding a new coinhistory entry with information: {}", add);

		Student student = userService.findByStudentId(studentId);
		add.setStudent(student);
        CoinHistory added = service.add(add);

        LOGGER.debug("Added a coinhistory entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/clazz/{clazzId}/coinhistories", method = RequestMethod.POST)
    @ResponseBody
    public CoinHistory addByClazz(@Valid @RequestBody CoinHistory coinHistory, @PathVariable("clazzId") long clazzId) {
    	List<Student> students = studentRepository.findByClazzId(clazzId);
    	for (Student student : students){
    		add(coinHistory, student.getId());
    	}
    	return coinHistory;
    }
    
    @RequestMapping(value = "/teachers/{teacherId}/coinhistories", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<CoinHistory> findCoinHistoriesByTeacherId( @PathVariable("teacherId") long teacherId,
														    	   @RequestParam("page") int page,
															       @RequestParam("size") int size,
															       @RequestParam("sortBy") String sortBy,
															       @RequestParam("direction") String direction) {
		Pageable pageable = getPageable(page, size, sortBy, direction);
		
        Page<CoinHistory> coinhistoryPage = service.findByTeacherId(pageable, teacherId);
        
        return new PageResource<>(coinhistoryPage, "page", "size");
    }
    
    @RequestMapping(value = "/class/{classId}/coinhistories", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<CoinHistory> findCoinHistoriesByClassId( @PathVariable("classId") long classId,
													    	     @RequestParam("page") int page,
														         @RequestParam("size") int size,
														         @RequestParam("sortBy") String sortBy,
														         @RequestParam("direction") String direction) {
		Pageable pageable = getPageable(page, size, sortBy, direction);
		
        Page<CoinHistory> coinhistoryPage = service.findByClassId(pageable, classId);
        
        return new PageResource<>(coinhistoryPage, "page", "size");
    }
    
    @RequestMapping(value = "/coinhistories", method = RequestMethod.GET)
    @ResponseBody
    public CoinHistoriesDTO findCoinHistories( @RequestParam("semesterId") long semesterId, 
			 								   @RequestParam("studentId") long studentId ) {
        Semester semester = semesterService.findOne(semesterId);
        
        List<CoinHistory> coinhistories = service.findBySemesterIdAndStudentId(semesterId, studentId);
        
        return new CoinHistoriesDTO(coinhistories, semester.getStartTime(), semester.getEndTime());
    }
    
    
//
//    @RequestMapping(value = "/api/coinhistories/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public CoinHistory deleteById(@PathVariable("id") Long id) throws NotFoundException {
//        LOGGER.debug("Deleting a coinhistory entry with id: {}", id);
//
//        CoinHistory deleted = service.deleteById(id);
//        LOGGER.debug("Deleted coinhistory entry with information: {}", deleted);
//
//        return deleted;
//    }
//        
//    @RequestMapping(value = "/api/coinhistories/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public CoinHistory update(@Valid @RequestBody CoinHistory updateEntity, @PathVariable("id") Long coinhistoryId) throws NotFoundException {
//        LOGGER.debug("Updating a coinhistory entry with information: {}", updateEntity);
//
//        CoinHistory updated = service.update(updateEntity);
//        LOGGER.debug("Updated the information of a coinhistory entry to: {}", updated);
//
//        return updated;
//    }
    

	public class CoinHistoriesDTO {
		
		public int[] monthlyCoins;
		
		public CoinHistoriesDTO ( List<CoinHistory> coinhistories, DateTime startTime, DateTime endTime ){
	    	
			// Initlizate the dto entity
			int startMonth = startTime.getMonthOfYear();
	    	int endMonth = endTime.getMonthOfYear();
	    	int monthCount = endMonth - startMonth + ( startMonth < endMonth ? 0 : 12 ) + 1;
	    	this.monthlyCoins = new int[monthCount];
	    	
	    	// Initlizate monthlyCoins and monthlyCoins
	    	for ( int i = 0; i < monthCount; i ++ ){
	    		this.monthlyCoins[i] = 0;
	    	}
			for ( CoinHistory coinHistory : coinhistories ) {
				
				// The index in both arrays
				int index = coinHistory.getCreationTime().getMonthOfYear() - startMonth;
				if ( index < 0 ) { index += 12; }
				
				// Update monthlyVerifiedNums
				this.monthlyCoins[index] ++;
			}
		}
	}
}
