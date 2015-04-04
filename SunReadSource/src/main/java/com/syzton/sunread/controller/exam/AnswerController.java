package com.syzton.sunread.controller.exam;

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
import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.service.exam.AnswerService;
import com.syzton.sunread.service.exam.ObjectiveAnswerService;
import com.syzton.sunread.service.exam.SubjectiveAnswerService;

@Controller
@RequestMapping(value = "/api")
public class AnswerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnswerController.class);

    private AnswerService service;
    
    private ObjectiveAnswerService objectiveService;
    
    private SubjectiveAnswerService subjectiveService;
     
    @Autowired
    public AnswerController(AnswerService service,ObjectiveAnswerService objService) {
        this.service = service;
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    @ResponseBody
    public AnswerDTO add(@Valid @RequestBody AnswerDTO dto) {
        LOGGER.debug("Adding a new to-do entry with information: {}", dto);

        Answer added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added.createDTO();
    }
    
    @RequestMapping(value = "/answer/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public AnswerDTO deleteById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Answer deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted.createDTO();
    }

    @RequestMapping(value = "/answers", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Answer> findAll(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) {
    	 LOGGER.debug("Finding answer entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<Answer> pageResult = service.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }
    
    //TODO wait user schoo module
    @RequestMapping(value = "/answers/{userid}", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Answer> findOtherPersonAnswer(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) {
    	 LOGGER.debug("Finding answer entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<Answer> pageResult = service.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/subjectiveanswers", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SubjectiveAnswer> findSubjectiveQuestions(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	 LOGGER.debug("Finding subjective answer entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<SubjectiveAnswer> pageResult = subjectiveService.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/objectiveanswers", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<ObjectiveAnswer> findObjectiveQuestions(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	 LOGGER.debug("Finding objective answer entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<ObjectiveAnswer> pageResult = objectiveService.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }

   
    @RequestMapping(value = "/answer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Answer  findById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Answer found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }

    @RequestMapping(value = "/answer/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Answer update(@Valid @RequestBody AnswerDTO dto, @PathVariable("id") Long todoId) throws AnswerNotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", dto);

        Answer updated = service.update(dto);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated;
    }

}
