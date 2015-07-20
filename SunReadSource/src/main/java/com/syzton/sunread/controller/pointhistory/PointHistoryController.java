package com.syzton.sunread.controller.pointhistory;

import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.model.pointhistory.PointHistory.PointType;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.service.pointhistory.PointHistoryService;
import com.syzton.sunread.service.semester.SemesterService;


/**
 * @author chenty
 *
 */
@Controller
public class PointHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointHistoryController.class);

    private PointHistoryService service;

    @Autowired
    public PointHistoryController(PointHistoryService service) {
        this.service = service;
    }
    
    private SemesterService semesterService;
    
    @Autowired
    public void SemesterServiceController(SemesterService semesterService){
    	this.semesterService = semesterService;    	
    }

    @RequestMapping(value = "/api/pointhistories", method = RequestMethod.POST)
    @ResponseBody
    public PointHistory add(@Valid @RequestBody PointHistory add) {
        LOGGER.debug("Adding a new pointhistory entry with information: {}", add);

        PointHistory added = service.add(add);

        LOGGER.debug("Added a pointhistory entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/api/semesters/{semesterId}/pointhistories", method = RequestMethod.GET)
    @ResponseBody
    public PointHistoriesDTO findPointHistoriesBySemesterId( @PathVariable("semesterId") long semesterId ) {

        Semester semester = semesterService.findOne(semesterId);
        List<PointHistory> pointhistories = service.findBySemesterId(semesterId);
        
        return new PointHistoriesDTO(pointhistories, semester.getStartTime(), semester.getEndTime());
    }
    
//
//    @RequestMapping(value = "/api/pointhistories/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public PointHistory deleteById(@PathVariable("id") Long id) throws NotFoundException {
//        LOGGER.debug("Deleting a pointhistory entry with id: {}", id);
//
//        PointHistory deleted = service.deleteById(id);
//        LOGGER.debug("Deleted pointhistory entry with information: {}", deleted);
//
//        return deleted;
//    }
//    
    @RequestMapping(value = "/api/users/{userId}/pointhistories", method = RequestMethod.GET)
    @ResponseBody
    public List<PointHistory> findPointHistoriesByStudentId( @PathVariable("studentId") long studentId ) {
        LOGGER.debug("Finding all pointhistory entries by userId.");

        List<PointHistory> models = service.findByStudentId(studentId);
        LOGGER.debug("Found {} pointhistory entries.", models.size());

        return models;
    }
//    
//    @RequestMapping(value = "/api/pointhistories/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public PointHistory update(@Valid @RequestBody PointHistory updateEntity, @PathVariable("id") Long pointhistoryId) throws NotFoundException {
//        LOGGER.debug("Updating a pointhistory entry with information: {}", updateEntity);
//
//        PointHistory updated = service.update(updateEntity);
//        LOGGER.debug("Updated the information of a pointhistory entry to: {}", updated);
//
//        return updated;
//    }
    
	public class PointHistoriesDTO {
		
		public int[] monthlyPoints;
		
		public PointHistoriesDTO ( List<PointHistory> pointhistories, DateTime startTime, DateTime endTime ){
	    	
			// Initlizate the dto entity
			int startMonth = startTime.getMonthOfYear();
	    	int endMonth = endTime.getMonthOfYear();
	    	int monthCount = endMonth - startMonth + ( startMonth < endMonth ? 0 : 12 ) + 1;
	    	this.monthlyPoints = new int[monthCount];
	    	
	    	// Initlizate monthlyPoints and monthlyCoins
	    	for ( int i = 0; i < monthCount; i ++ ){
	    		this.monthlyPoints[i] = 0;
	    	}
			for ( PointHistory pointHistory : pointhistories ) {
				
				// The index in both arrays
				int index = pointHistory.getCreationTime().getMonthOfYear() - startMonth;
				if ( index < 0 ) { index += 12; }
				
				// Update monthlyVerifiedNums
				this.monthlyPoints[index] += pointHistory.getPointType() == PointType.IN ? pointHistory.getNum() : - pointHistory.getNum() ;
			}
		}
	}
}