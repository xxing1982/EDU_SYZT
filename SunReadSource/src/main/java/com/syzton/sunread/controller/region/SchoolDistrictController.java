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
import com.syzton.sunread.dto.region.SchoolDistrictDTO;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.region.SchoolDistrict;
import com.syzton.sunread.service.region.SchoolDistrictService;

@Controller
@RequestMapping(value="/api")
public class SchoolDistrictController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolDistrictController.class);
    private SchoolDistrictService service;
    @Autowired
    public  SchoolDistrictController(SchoolDistrictService service){
    	this.service = service;
    }
    
//Add a SchoolDistrict
    @RequestMapping(value = "/region/{regionId}/schoolDistrict", method = RequestMethod.POST)
    @ResponseBody
    public SchoolDistrictDTO add(@Valid @RequestBody SchoolDistrictDTO dto
    		,@PathVariable("regionId")Long regionId) {
        LOGGER.debug("Adding a new Schooldistrict entry with information: {}", dto);
        
        SchoolDistrict added = service.add(dto,regionId);
        LOGGER.debug("Added a Schooldistrict entry with information: {}", added);
              
       return added.createDTO(added);
    }
    
    
//Delete a SchoolDistrict
    @RequestMapping(value = "/schoolDistrict/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public SchoolDistrictDTO deleteById(@Valid @PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a SchoolDistrict entry with id: {}", id);

        SchoolDistrict deleted = service.deleteById(id);
        LOGGER.debug("Deleted SchoolDistrict entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
//Update a SchoolDistrict    
    @RequestMapping(value = "/schoolDistrict/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public SchoolDistrictDTO update(@Valid @RequestBody SchoolDistrictDTO dto,@PathVariable("id") long id) throws NotFoundException {
        LOGGER.debug("Adding a new book to shelf entry with information: {}", dto);
        
        SchoolDistrict updated = service.update(dto);
        LOGGER.debug("Added a to-do entry with information: {}", updated);
              
       return updated.createDTO(updated);
    } 

//Get all SchoolDistricts
    @RequestMapping(value = "/schoolDistricts", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SchoolDistrict> findAll(
    						@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding  edugroups entry " );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<SchoolDistrict> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
//Get a SchoolDistrict   
    @RequestMapping(value = "/schoolDistrict/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SchoolDistrictDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding a edugroup entry with id: {}", id);

        SchoolDistrict found = service.findById(id);
        LOGGER.debug("Found edugroup entry with information: {}", found);

        return found.createDTO(found);
    }
    
    @RequestMapping(value = "/schooldistricts/search", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SchoolDistrict> searchCampusQuestions(@RequestParam("name") String name,@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	sortBy = sortBy==null?"id": sortBy;
        
        Pageable pageable = new PageRequest(page,size,new Sort(sortBy));
        Page<SchoolDistrict> pageResult = service.searchSchoolDistrictsByName(name,pageable);

        return new PageResource<>(pageResult,"page","size");
    }
}
