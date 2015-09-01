package com.syzton.sunread.controller.organization;

import java.util.List;

import javassist.NotFoundException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.clazz.ClazzSumStatisticDTO;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.organization.ClazzDTO;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.service.organization.ClazzService;

@Controller
@RequestMapping(value="/api")
public class ClazzController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzController.class);
    private ClazzService service;
    @Autowired
    public  ClazzController(ClazzService service){
    	this.service = service;
    }
    
//Add a Clazz 
    @RequestMapping(value = "/campus/{id}/clazz", method = RequestMethod.POST)
    @ResponseBody
    public ClazzDTO add(@Valid @RequestBody ClazzDTO dto
    		,@PathVariable("id")Long id) throws NotFoundException{
        LOGGER.debug("Adding a new edu group entry with information: {}", dto);
        
        Clazz added = service.add(dto, id);
        LOGGER.debug("Added a edu group entry with information: {}", added);
              
       return added.createDTO(added);
    }
    
    
//Delete a Clazz
    @RequestMapping(value = "/clazz/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ClazzDTO deleteById(@Valid @PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a Clazz entry with id: {}", id);

        Clazz deleted = service.deleteById(id);
        LOGGER.debug("Deleted Clazz entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update a Clazz    
    @RequestMapping(value = "/clazz/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ClazzDTO update(@Valid @RequestBody ClazzDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        Clazz updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    } 

//Get all Clazzs
    @RequestMapping(value = "/clazzs", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Clazz> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  edugroups entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<Clazz> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }

    @RequestMapping(value = "/campuses/{campusId}/hotclazzs", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Clazz> findAll(@PathVariable("campusId") long campusId,
            @RequestParam("page") int page,
            @RequestParam("size") int size) throws NotFoundException {
        Pageable pageable = this.getPageable(page,size,"clazzStatistic.avgPoints","desc");
        Page<Clazz> pageResult = service.findByCampus(campusId,pageable);
        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a Clazz by id 
    @RequestMapping(value = "/clazz/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ClazzDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        Clazz found = service.findById(id);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO(found);
    }
    
//Get a Clazz by name 
    @RequestMapping(value = "/clazz", method = RequestMethod.GET)
    @ResponseBody
    public ClazzDTO findById(@RequestParam("clazzName")String clazzName) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", clazzName);

        Clazz found = service.findByClazzName(clazzName);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO(found);
    }
    
    @RequestMapping(value = "/clazz/averagepoint/{id}", method = RequestMethod.GET)
    @ResponseBody
    public int findAveragePointsFromClass(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        int averagePoint = service.getAveragePointsfromClass(id);
        LOGGER.debug("Found class average point information: {}", averagePoint);

        return averagePoint;
    }
    
    @RequestMapping(value = "/clazz/averagereadingbook/{id}", method = RequestMethod.GET)
    @ResponseBody
    public int findAverageReadingBookFromClass(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);
        int averagePoint = service.getAverageReadingBookFromClass(id);
        LOGGER.debug("Found class average point information: {}", averagePoint);

        return averagePoint;
    }

    @RequestMapping(value = "/sumStatistics", method = RequestMethod.GET)
    @ResponseBody
    public ClazzSumStatisticDTO sumStatistic(@RequestParam("grade") int grade,
    		                                 @RequestParam("campusId") long campusId) throws NotFoundException {
        return service.getSumClazzStatistic(grade, campusId);

    }
    @RequestMapping(value = "/grade/{id}/clazzs", method = RequestMethod.GET)
    @ResponseBody
    public List<Clazz> findClazzsByGrade(@PathVariable("id") int id) throws NotFoundException {
        return service.findByGrade(id);

    }
    
    @RequestMapping(value = "/campus/{id}/clazzs", method = RequestMethod.GET)
    @ResponseBody
    public List<Clazz> findClazzsByCampus(@PathVariable("id") long id) throws NotFoundException {
        return service.findByCampus(id);

    }
    
    @RequestMapping(value = "/clazzs/{clazzId}/upgrade", method = RequestMethod.PUT)
    @ResponseBody
    public Clazz gradeUpgrade(@PathVariable("clazzId") long clazzId) throws NotFoundException {
        return service.clazzUpgrade(clazzId);
    }
}
