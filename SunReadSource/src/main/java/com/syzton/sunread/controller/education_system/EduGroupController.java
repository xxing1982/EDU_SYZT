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
import com.syzton.sunread.dto.education_system.EduGroupDTO;
import com.syzton.sunread.model.education_system.EduGroup;
import com.syzton.sunread.service.education_system.EduGroupService;

@Controller
@RequestMapping(value="/api")
public class EduGroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EduGroupController.class);
    private EduGroupService service;
    @Autowired
    public  EduGroupController(EduGroupService service){
    	this.service = service;
    }
    
//Add a EduGroup 
    @RequestMapping(value = "/eduGroup", method = RequestMethod.POST)
    @ResponseBody
    public EduGroupDTO add(@Valid @RequestBody EduGroupDTO dto) {
        LOGGER.debug("Adding a new edu group entry with information: {}", dto);
        
        EduGroup added = service.add(dto);
        LOGGER.debug("Added a edu group entry with information: {}", added);
              
       return added.createDTO(added);
    }
    
    
//Delete a Edu Group
    @RequestMapping(value = "/eduGroup/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public EduGroupDTO deleteById(@Valid @PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a Edu Group entry with id: {}", id);

        EduGroup deleted = service.deleteById(id);
        LOGGER.debug("Deleted Edu Group entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update a Edu Group    
    @RequestMapping(value = "/eduGroup/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public EduGroupDTO update(@Valid @RequestBody EduGroupDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        EduGroup updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    } 

//Get all EduGroups
    @RequestMapping(value = "/eduGroups", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<EduGroup> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  edugroups entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<EduGroup> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a EduGroup    
    @RequestMapping(value = "/eduGroup/{id}", method = RequestMethod.GET)
    @ResponseBody
    public EduGroupDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        EduGroup found = service.findById(id);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO(found);
    }
    
    
}
