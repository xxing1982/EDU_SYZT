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
import com.syzton.sunread.dto.organization.CompusDTO;
import com.syzton.sunread.model.organization.Compus;
import com.syzton.sunread.service.organization.CompusService;

/**
 * @author Morgan-Leon
 * @Date 2015年4月7日
 * 
 */

@Controller
@RequestMapping(value="/api")
public class CompusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompusController.class);
    private CompusService service;
    @Autowired
    public  CompusController(CompusService service){
    	this.service = service;
    }
    
//Add a Compus 
    @RequestMapping(value = "/region/{regionId}/school/{schoolId}/compus", method = RequestMethod.POST)
    @ResponseBody
    public CompusDTO add(@Valid @RequestBody CompusDTO dto
    		,@PathVariable("regionId")Long regionId,@PathVariable("schoolId")Long schoolId) {
        LOGGER.debug("Adding a new compus entry with information: {}", dto);
        
        Compus added = service.add(dto, regionId,schoolId);
        LOGGER.debug("Added a compus entry with information: {}", added);
              
       return added.createDTO(added);
    }
    
    
//Delete a Compus
    @RequestMapping(value = "/compus/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CompusDTO deleteById(@Valid @PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a Compus entry with id: {}", id);

        Compus deleted = service.deleteById(id);
        LOGGER.debug("Deleted Compus entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update a Compus    
    @RequestMapping(value = "/compus/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CompusDTO update(@Valid @RequestBody CompusDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        Compus updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    } 

//Get all Compuss
    @RequestMapping(value = "/compuss", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Compus> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  edugroups entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<Compus> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a Compus    
    @RequestMapping(value = "/compus/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CompusDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        Compus found = service.findById(id);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO(found);
    }
    
    
}
