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
import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.exception.answer.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.service.exam.AnswerService;
import com.syzton.sunread.service.exam.ExamService;
import com.syzton.sunread.service.exam.ObjectiveAnswerService;
import com.syzton.sunread.service.exam.SubjectiveAnswerService;

@Controller
public class ExamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    private ExamService service;
    
     
    @Autowired
    public ExamController(ExamService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/exam", method = RequestMethod.POST)
    @ResponseBody
    public ExamDTO add(@Valid @RequestBody ExamDTO dto) {
        LOGGER.debug("Adding a new to-do entry with information: {}", dto);

        Exam added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added.createDTO();
    }
    
    @RequestMapping(value = "/api/exam/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ExamDTO deleteById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Exam deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO();
    }

    @RequestMapping(value = "/api/exam", method = RequestMethod.GET)
    @ResponseBody
    public List<ExamDTO> findAll() {
        LOGGER.debug("Finding all todo entries.");

        List<Exam> models = service.findAll();
        LOGGER.debug("Found {} to-do entries.", models.size());

        return createDTOs(models);
    }

    private List<ExamDTO> createDTOs(List<Exam> models) {
        List<ExamDTO> dtos = new ArrayList<ExamDTO>();

        for (Exam model: models) {
            dtos.add(model.createDTO());
        }

        return dtos;
    }

    @RequestMapping(value = "/api/exam/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ExamDTO findById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Exam found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO();
    }

    @RequestMapping(value = "/api/exam/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ExamDTO update(@Valid @RequestBody ExamDTO dto, @PathVariable("id") Long todoId) throws AnswerNotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", dto);

        Exam updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated.createDTO();
    }
}
