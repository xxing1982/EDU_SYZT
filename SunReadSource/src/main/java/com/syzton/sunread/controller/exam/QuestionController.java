package com.syzton.sunread.controller.exam;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.exception.answer.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.service.exam.ExamService;
import com.syzton.sunread.service.exam.QuestionService;

public class QuestionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    private QuestionService service;
    
     
    @Autowired
    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/question", method = RequestMethod.POST)
    @ResponseBody
    public QuestionDTO add(@Valid @RequestBody QuestionDTO dto) {
        LOGGER.debug("Adding a new to-do entry with information: {}", dto);

        Question added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added.createDTO();
    }
    
    @RequestMapping(value = "/api/question/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public QuestionDTO deleteById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Question deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO();
    }

    @RequestMapping(value = "/api/question", method = RequestMethod.GET)
    @ResponseBody
    public List<QuestionDTO> findAll() {
        LOGGER.debug("Finding all todo entries.");

        List<Question> models = service.findAll();
        LOGGER.debug("Found {} to-do entries.", models.size());

        return createDTOs(models);
    }

    private List<QuestionDTO> createDTOs(List<Question> models) {
        List<QuestionDTO> dtos = new ArrayList<QuestionDTO>();

        for (Question model: models) {
            dtos.add(model.createDTO());
        }

        return dtos;
    }

    @RequestMapping(value = "/api/question/{id}", method = RequestMethod.GET)
    @ResponseBody
    public QuestionDTO findById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Question found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO();
    }

    @RequestMapping(value = "/api/question/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public QuestionDTO update(@Valid @RequestBody QuestionDTO dto, @PathVariable("id") Long todoId) throws AnswerNotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", dto);

        Question updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated.createDTO();
    }
}
