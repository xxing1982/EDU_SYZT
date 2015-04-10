package com.syzton.sunread.controller.coinhistory;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.service.coinhistory.CoinHistoryService;
import javax.validation.Valid;


/**
 * @author chenty
 *
 */
@Controller
public class CoinHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinHistoryController.class);

    private CoinHistoryService service;

    @Autowired
    public CoinHistoryController(CoinHistoryService service) {
        this.service = service;
    }

//    @RequestMapping(value = "/api/coinhistories", method = RequestMethod.POST)
//    @ResponseBody
//    public CoinHistory add(@Valid @RequestBody CoinHistory add) {
//        LOGGER.debug("Adding a new coinhistory entry with information: {}", add);
//
//        CoinHistory added = service.add(add);
//
//        LOGGER.debug("Added a coinhistory entry with information: {}", added);
//
//       return added;
//    }
//
//    @RequestMapping(value = "/api/coinhistories/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public CoinHistory deleteById(@PathVariable("id") Long id) throws NotFoundException {
//        LOGGER.debug("Deleting a coinhistory entry with id: {}", id);
//
//        CoinHistory deleted = service.deleteById(id);
//        LOGGER.debug("Deleted coinhistory entry with information: {}", deleted);
//
//        return deleted;
//    }
//    
    @RequestMapping(value = "/api/users/{userId}/coinhistories", method = RequestMethod.GET)
    @ResponseBody
    public List<CoinHistory> findAll() {
        LOGGER.debug("Finding all coinhistory entries.");

        List<CoinHistory> models = service.findAll();
        LOGGER.debug("Found {} coinhistory entries.", models.size());

        return models;
    }
    
//    @RequestMapping(value = "/api/coinhistories/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public CoinHistory update(@Valid @RequestBody CoinHistory updateEntity, @PathVariable("id") Long coinhistoryId) throws NotFoundException {
//        LOGGER.debug("Updating a coinhistory entry with information: {}", updateEntity);
//
//        CoinHistory updated = service.update(updateEntity);
//        LOGGER.debug("Updated the information of a coinhistory entry to: {}", updated);
//
//        return updated;
//    }
}
