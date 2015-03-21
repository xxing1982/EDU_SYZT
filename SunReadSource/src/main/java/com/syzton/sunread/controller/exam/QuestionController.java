package com.syzton.sunread.controller.exam;

import java.util.ArrayList;
import java.util.List;

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
import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.repository.exam.SubjectiveQuestionRepository;
import com.syzton.sunread.service.exam.ExamService;
import com.syzton.sunread.service.exam.ObjectiveQuestionService;
import com.syzton.sunread.service.exam.QuestionService;
import com.syzton.sunread.service.exam.SubjectiveQuestionService;

@Controller
@RequestMapping(value = "/api")
public class QuestionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    private QuestionService service;
    
    private ObjectiveQuestionService objectService;
    
    private SubjectiveQuestionService subjectService;
    
     
    @Autowired
    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @ResponseBody
    public QuestionDTO add(@Valid @RequestBody QuestionDTO dto) {
        LOGGER.debug("Adding a new to-do entry with information: {}", dto);

        Question added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added.createDTO();
    }
    
    @RequestMapping(value = "/question/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public QuestionDTO deleteById(@PathVariable("id") Long id) throws QuestionNotFoundExcepiton {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Question deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO();
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Question> findAll(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) {
    	LOGGER.debug("Finding objective question entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<Question> pageResult = service.findAll(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/subjectivequestion", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SubjectiveQuestion> findSubjectiveQuestions(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	 LOGGER.debug("Finding objective question entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<SubjectiveQuestion> pageResult = subjectService.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/objectivequestion", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<ObjectiveQuestion> findObjectiveQuestions(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	 LOGGER.debug("Finding objective question entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<ObjectiveQuestion> pageResult = objectService.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }

    private List<QuestionDTO> createDTOs(List<Question> models) {
        List<QuestionDTO> dtos = new ArrayList<QuestionDTO>();

        for (Question model: models) {
            dtos.add(model.createDTO());
        }

        return dtos;
    }

    @RequestMapping(value = "/question/{id}", method = RequestMethod.GET)
    @ResponseBody
    public QuestionDTO findById(@PathVariable("id") Long id) throws QuestionNotFoundExcepiton {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Question found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found.createDTO();
    }

    @RequestMapping(value = "/question/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public QuestionDTO update(@Valid @RequestBody QuestionDTO dto, @PathVariable("id") Long todoId) throws QuestionNotFoundExcepiton {
        LOGGER.debug("Updating a to-do entry with information: {}", dto);

        Question updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated.createDTO();
    }
}