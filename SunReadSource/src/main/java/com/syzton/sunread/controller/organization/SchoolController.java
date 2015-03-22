package com.syzton.sunread.controller.organization;

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
import com.syzton.sunread.dto.organization.SchoolDTO;
import com.syzton.sunread.model.organization.School;
import com.syzton.sunread.service.organization.SchoolService;

@Controller
@RequestMapping(value="/api")
public class SchoolController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);
    private SchoolService service;
    @Autowired
    public  SchoolController(SchoolService service){
    	this.service = service;
    }
    
//Add a School 
    @RequestMapping(value = "/eduGroup/{edu_id}/school", method = RequestMethod.POST)
    @ResponseBody
    public SchoolDTO add(@Valid @RequestBody SchoolDTO dto
    		,@PathVariable("edu_id")Long eduId) {
        LOGGER.debug("Adding a new edu group entry with information: {}", dto);
        
        School added = service.add(dto,eduId);
        LOGGER.debug("Added a edu group entry with information: {}", added);
              
       return added.createDTO(added);
    }
    
    
//Delete a School
    @RequestMapping(value = "/school/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public SchoolDTO deleteById(@Valid @PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a School entry with id: {}", id);

        School deleted = service.deleteById(id);
        LOGGER.debug("Deleted School entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update a School    
    @RequestMapping(value = "/school/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public SchoolDTO update(@Valid @RequestBody SchoolDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        School updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    } 

//Get all Schools
    @RequestMapping(value = "/schools", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<School> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  edugroups entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<School> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a School    
    @RequestMapping(value = "/school/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SchoolDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        School found = service.findById(id);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO(found);
    }
    
    
}
