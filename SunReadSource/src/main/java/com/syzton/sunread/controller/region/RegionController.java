package com.syzton.sunread.controller.region;

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
import com.syzton.sunread.dto.region.RegionDTO;
import com.syzton.sunread.model.region.Region;
import com.syzton.sunread.service.region.RegionService;


/**
 * @author Morgan-Leon
 * @Date 2015年3月24日
 * 
 */
@Controller
@RequestMapping(value = "/api")
public class RegionController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionController.class);
    private RegionService service;
    @Autowired
    public  RegionController(RegionService service){
    	this.service = service;
    }
    
//Add a Region 
    @RequestMapping(value = "/region", method = RequestMethod.POST)
    @ResponseBody
    public Region add(@Valid @RequestBody Region region) {
        LOGGER.debug("Adding a new region entry with information: {}", region);
        
        Region added = service.add(region);
        LOGGER.debug("Added a region entry with information: {}", added);
              
       return added;
    }
    
    
//Delete a region
    @RequestMapping(value = "/region/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Region deleteById(@Valid @PathVariable("id") Long id) {
        LOGGER.debug("Deleting a region entry with id: {}", id);

        Region deleted = service.deleteById(id);
        LOGGER.debug("Deleted region entry with information: {}", deleted);

        return deleted;
    }
    
//Update a region    
    @RequestMapping(value = "/region/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Region update(@Valid @RequestBody Region region,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new region entry with information: {}", region);
        
        Region updated = service.update(region);
        LOGGER.debug("Added a region with information: {}", updated);
              
       return updated;
    } 

//Get all Regions
    @RequestMapping(value = "/regions", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Region> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam(value = "sortBy",required = false) String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  regions entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<Region> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a Region    
    @RequestMapping(value = "/region/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Region findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a region entry with id: {}", id);

        Region found = service.findOne(id);
        LOGGER.debug("Found region entry with information: {}", found);

        return found;
    }
}
