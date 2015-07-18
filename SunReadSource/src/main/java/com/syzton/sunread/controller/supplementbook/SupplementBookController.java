package com.syzton.sunread.controller.supplementbook;

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
import com.syzton.sunread.dto.supplementbook.SupplementBookDTO;
import com.syzton.sunread.model.supplementbook.SupplementBook;
import com.syzton.sunread.service.supplementbook.SupplementBookService;

/**
 * @author Morgan-Leon
 * @date 2015-03-12
 */
@Controller
public class SupplementBookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplementBookController.class);

    private SupplementBookService service;

    @Autowired
    public SupplementBookController(SupplementBookService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/supplementbooks", method = RequestMethod.POST)
    @ResponseBody
    public SupplementBookDTO add(@Valid @RequestBody SupplementBookDTO dto) {
        LOGGER.debug("Adding a new book entry with information: {}", dto);

        SupplementBook added = service.add(dto);

        LOGGER.debug("Added a book entry with information: {}", added);

        return added.createDTO(added);
    }

    @RequestMapping(value = "/api/supplementbooks", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SupplementBook> findAllSupplementBooks(@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<SupplementBook> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }


    @RequestMapping(value = "/api/supplementbooks/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SupplementBookDTO findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        SupplementBook found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO(found);
    }



    @RequestMapping(value = "/api/supplementbooks/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public SupplementBookDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        SupplementBook deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
}
