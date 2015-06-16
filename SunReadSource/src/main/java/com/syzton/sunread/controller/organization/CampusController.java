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
import com.syzton.sunread.dto.organization.CampusDTO;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.service.organization.CampusService;

/**
 * @author Morgan-Leon
 * @Date 2015年4月7日
 * 
 */

@Controller
@RequestMapping(value="/api")
public class CampusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampusController.class);
    private CampusService service;
    @Autowired
    public  CampusController(CampusService service){
    	this.service = service;
    }
    
//Add a Campus 
    @RequestMapping(value = "/region/{regionId}/campus", method = RequestMethod.POST)
    @ResponseBody
    public CampusDTO add(@Valid @RequestBody CampusDTO dto
    		,@PathVariable("regionId")Long regionId) {
        LOGGER.debug("Adding a new campus entry with information: {}", dto);
        
        Campus added = service.add(dto,regionId);
        LOGGER.debug("Added a campus entry with information: {}", added);
              
       return added.createDTO();
    }
    
    
//Delete a Campus
    @RequestMapping(value = "/campus/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CampusDTO deleteById(@Valid @PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a Campus entry with id: {}", id);

        Campus deleted = service.deleteById(id);
        LOGGER.debug("Deleted Campus entry with information: {}", deleted);

        return deleted.createDTO();
    }
    
//Update a Campus    
    @RequestMapping(value = "/campus/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CampusDTO update(@Valid @RequestBody CampusDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        Campus updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO();
    } 

//Get all Campuss
    @RequestMapping(value = "/campuss", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Campus> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  edugroups entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<Campus> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a Campus by id  
    @RequestMapping(value = "/campus/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CampusDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        Campus found = service.findById(id);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO();
    }
    
 //Get a Campus by Name   
    @RequestMapping(value = "/campus", method = RequestMethod.GET)
    @ResponseBody
    public CampusDTO findById(@RequestParam("campusName") String campusName) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", campusName);

        Campus found = service.findByCompusName(campusName);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO();
    }
    
    
}
