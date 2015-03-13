package com.syzton.sunread.controller.exam;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.answer.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.service.exam.AnswerService;
import com.syzton.sunread.service.exam.ObjectiveAnswerService;
import com.syzton.sunread.service.exam.SubjectiveAnswerService;

@Controller
public class AnswerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnswerController.class);

    private AnswerService service;
    
    private ObjectiveAnswerService objectiveService;
    
    private SubjectiveAnswerService subjectiveService;
     
    @Autowired
    public AnswerController(AnswerService service,ObjectiveAnswerService objService) {
        this.service = service;
    }

    @RequestMapping(value = "/api/answer", method = RequestMethod.POST)
    @ResponseBody
    public AnswerDTO add(@Valid @RequestBody AnswerDTO dto) {
        LOGGER.debug("Adding a new to-do entry with information: {}", dto);

        Answer added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added.createDTO();
    }
    
    @RequestMapping(value = "/api/answer/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AnswerDTO deleteById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Answer deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO();
    }

    @RequestMapping(value = "/api/answer", method = RequestMethod.GET)
    @ResponseBody
    public List<AnswerDTO> findAll() {
        LOGGER.debug("Finding all todo entries.");

        List<Answer> models = service.findAll();
        LOGGER.debug("Found {} to-do entries.", models.size());

        return createDTOs(models);
    }

    private List<AnswerDTO> createDTOs(List<Answer> models) {
        List<AnswerDTO> dtos = new ArrayList<AnswerDTO>();

        for (Answer model: models) {
            dtos.add(model.createDTO());
        }

        return dtos;
    }

    @RequestMapping(value = "/api/answer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AnswerDTO findById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Answer found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO();
    }

    @RequestMapping(value = "/api/answer/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public AnswerDTO update(@Valid @RequestBody AnswerDTO dto, @PathVariable("id") Long todoId) throws AnswerNotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", dto);

        Answer updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated.createDTO();
    }

}
