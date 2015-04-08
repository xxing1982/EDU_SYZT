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
import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
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
    public QuestionController(QuestionService service,ObjectiveQuestionService objectService,SubjectiveQuestionService subjectService) {
        this.service = service;
        this.objectService = objectService;
        this.subjectService = subjectService;
    }

    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @ResponseBody
    public Question add(@Valid @RequestBody Question dto) {
        LOGGER.debug("Adding a new to-do entry with information: {}", dto);

        Question added = service.add(dto);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added;
    }
    
    
    @RequestMapping(value = "/objectivequestion", method = RequestMethod.POST)
    @ResponseBody
    public ObjectiveQuestion add(@Valid @RequestBody ObjectiveQuestion objectiveQuestion) {
        LOGGER.debug("Adding a new to-do entry with information: {}", objectiveQuestion);
        ObjectiveQuestion added = objectService.add(objectiveQuestion);
        LOGGER.debug("Added a to-do entry with information: {}", added);
       return added;
    }
    
    
    @RequestMapping(value = "/capacityquestion", method = RequestMethod.POST)
    @ResponseBody
    public CapacityQuestion add(@Valid @RequestBody CapacityQuestion question) {
        LOGGER.debug("Adding a new to-do entry with information: {}", question);

        CapacityQuestion added = objectService.addCapacityQuestion(question);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/subjectivequestion", method = RequestMethod.POST)
    @ResponseBody
    public SubjectiveQuestion add(@Valid @RequestBody SubjectiveQuestion question) {
        LOGGER.debug("Adding a new to-do entry with information: {}", question);

        SubjectiveQuestion added = subjectService.add(question);
        LOGGER.debug("Added a to-do entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/option", method = RequestMethod.POST)
    @ResponseBody
    public Option add(@Valid @RequestBody Option option) {
        LOGGER.debug("Adding a new to-do entry with information: {}", option);
        Option added = objectService.addOption(option);
        LOGGER.debug("Added a to-do entry with information: {}", added);
       return added;
    }
    
    @RequestMapping(value = "/questionoption/{objectiveId}/{optionId}", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectiveQuestion add(@PathVariable("objectiveId") Long objectiveId,@PathVariable("optionId") Long optionId) throws NotFoundException {
        LOGGER.debug("Adding option "+optionId+" to objective question "+ objectiveId +" with information: {}");
        ObjectiveQuestion added = objectService.setCorrectOption(objectiveId, optionId);
        LOGGER.debug("Added a to-do entry with information: {}", added);
       return added;
    }
    
   
    
    
    @RequestMapping(value = "/question/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Question deleteById(@PathVariable("id") Long id) throws QuestionNotFoundExcepiton {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Question deleted = service.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted;
    }
    
    @RequestMapping(value = "/objectivequestion/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectiveQuestion deleteObQuestionById(@PathVariable("id") Long id) throws NotFoundException  {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        ObjectiveQuestion deleted = objectService.deleteById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted;
    }
    
    @RequestMapping(value = "/capacityquestion/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public CapacityQuestion deleteCaQuestionById(@PathVariable("id") Long id) throws NotFoundException  {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        CapacityQuestion deleted = objectService.deleteCapacityQuestionById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted;
    }
    
    @RequestMapping(value = "/option/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Option deleteOptionById(@PathVariable("id") Long id) throws NotFoundException  {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        Option deleted = objectService.deleteOptionById(id);
        LOGGER.debug("Deleted to-do entry with information: {}", deleted);

        return deleted;
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
    
    @RequestMapping(value = "/objectivequestions", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<ObjectiveQuestion> findAllObjectiveQuestions(@RequestParam("page") int page,
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
    
    @RequestMapping(value = "/capacityquestions", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<CapacityQuestion> findAllCapacityQuestions(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	LOGGER.debug("Finding objective question entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<CapacityQuestion> pageResult = objectService.findAllCapacityQuestion(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/options", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Option> findAllOptions(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	LOGGER.debug("Finding objective question entry with id: {}" );
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<Option> pageResult = objectService.findAllOption(pageable);

        return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/subjectivequestions", method = RequestMethod.GET)
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
    
   


    @RequestMapping(value = "/question/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Question findById(@PathVariable("id") Long id) throws QuestionNotFoundExcepiton {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Question found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }
    
    @RequestMapping(value = "/objectivequestion/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectiveQuestion findByObjectiveId(@PathVariable("id") Long id) throws NotFoundException  {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        ObjectiveQuestion found = objectService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }
    
    
    @RequestMapping(value = "/subjectivequestion/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SubjectiveQuestion findBySubjectiveId(@PathVariable("id") Long id) throws NotFoundException   {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        SubjectiveQuestion found = subjectService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }
    
    
    @RequestMapping(value = "/capacityquestion/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CapacityQuestion findByCapacityId(@PathVariable("id") Long id) throws NotFoundException  {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        CapacityQuestion found = objectService.findCapacityQuestionById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }
    
    
    @RequestMapping(value = "/option/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Option findOptionById(@PathVariable("id") Long id) throws NotFoundException   {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Option found = objectService.findOptionById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }

    @RequestMapping(value = "/question/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Question update(@Valid @RequestBody Question  question, @PathVariable("id") Long todoId) throws QuestionNotFoundExcepiton {
        LOGGER.debug("Updating a to-do entry with information: {}", question);

        Question updated = service.update(question);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated;
    }
    
    @RequestMapping(value = "/objectivequestion/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectiveQuestion updateObjectiveQuestion(@Valid @RequestBody ObjectiveQuestion  question, @PathVariable("id") Long todoId) throws NotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", question);

        ObjectiveQuestion updated = objectService.update(question);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated;
    }
    
    @RequestMapping(value = "/capacityquestion/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CapacityQuestion updateCapacityQuestion(@Valid @RequestBody CapacityQuestion  question, @PathVariable("id") Long todoId) throws NotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", question);

        CapacityQuestion updated = objectService.updateCapacityQuestion(question);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated;
    }
    
    @RequestMapping(value = "/option/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Option update(@Valid @RequestBody Option  option, @PathVariable("id") Long todoId) throws NotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", option);

        Option updated = objectService.updateOption(option);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated;
    }
    
    @RequestMapping(value = "/subjectivequestion/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public SubjectiveQuestion update(@Valid @RequestBody SubjectiveQuestion  question, @PathVariable("id") Long todoId) throws NotFoundException {
        LOGGER.debug("Updating a to-do entry with information: {}", question);

        SubjectiveQuestion updated = subjectService.update(question);
        LOGGER.debug("Updated the information of a to-entry to: {}", updated);

        return updated;
    }
}
