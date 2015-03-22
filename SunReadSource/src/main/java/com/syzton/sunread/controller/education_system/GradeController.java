package com.syzton.sunread.controller.education_system;

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

import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.education_system.GradeDTO;
import com.syzton.sunread.model.education_system.Grade;
import com.syzton.sunread.service.education_system.GradeService;

@Controller
@RequestMapping(value="/api")
public class GradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GradeController.class);
    private GradeService service;
    @Autowired
    public  GradeController(GradeService service){
    	this.service = service;
    }
    
//Add a Grade 
    @RequestMapping(value = "/grade", method = RequestMethod.POST)
    @ResponseBody
    public GradeDTO add(@Valid @RequestBody GradeDTO dto) {
        LOGGER.debug("Adding a new edu group entry with information: {}", dto);
        
        Grade added = service.add(dto);
        LOGGER.debug("Added a edu group entry with information: {}", added);
              
       return added.createDTO(added);
    }
    
    
//Delete a Grade
    @RequestMapping(value = "/grade/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public GradeDTO deleteById(@Valid @PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a Grade entry with id: {}", id);

        Grade deleted = service.deleteById(id);
        LOGGER.debug("Deleted Grade entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update a Grade    
    @RequestMapping(value = "/grade/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public GradeDTO update(@Valid @RequestBody GradeDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        Grade updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    } 

//Get all Grades
    @RequestMapping(value = "/grades", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Grade> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  edugroups entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<Grade> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a Grade    
    @RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
    @ResponseBody
    public GradeDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        Grade found = service.findById(id);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO(found);
    }
    
    
}
