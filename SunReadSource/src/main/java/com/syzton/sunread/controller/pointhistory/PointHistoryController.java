package com.syzton.sunread.controller.pointhistory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.service.pointhistory.PointHistoryService;


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

//
//    @RequestMapping(value = "/api/pointhistories", method = RequestMethod.POST)
//    @ResponseBody
//    public PointHistory add(@Valid @RequestBody PointHistory add) {
//        LOGGER.debug("Adding a new pointhistory entry with information: {}", add);
//
//        PointHistory added = service.add(add);
//
//        LOGGER.debug("Added a pointhistory entry with information: {}", added);
//
//       return added;
//    }
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
}
