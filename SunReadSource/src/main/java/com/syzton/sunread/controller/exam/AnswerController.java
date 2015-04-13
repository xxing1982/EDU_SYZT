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
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
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
    public AnswerController(AnswerService service,ObjectiveAnswerService objService,SubjectiveAnswerService subjectiveService) {
        this.service = service;
        this.objectiveService = objService;
        this.subjectiveService = subjectiveService;
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    @ResponseBody
    public Answer add(@Valid @RequestBody Answer answer) {
        LOGGER.debug("Adding a new answer entry with information: {}", answer);

        Answer added = service.add(answer);
        LOGGER.debug("Added a answer entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/subjectiveanswer", method = RequestMethod.POST)
    @ResponseBody
    public SubjectiveAnswer addSubjectiveAnswer(@Valid @RequestBody SubjectiveAnswer answer) {
        LOGGER.debug("Adding a new answer entry with information: {}", answer);

        SubjectiveAnswer added = subjectiveService.add(answer);
        LOGGER.debug("Added a answer entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/objectiveanswer", method = RequestMethod.POST)
    @ResponseBody
    public ObjectiveAnswer addObjectiveAnswer(@Valid @RequestBody ObjectiveAnswer answer) {
        LOGGER.debug("Adding a new answer entry with information: {}", answer);

        ObjectiveAnswer added = objectiveService.add(answer);
        LOGGER.debug("Added a answer entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/answer/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Answer deleteById(@PathVariable("id") Long id) throws AnswerNotFoundException {
        LOGGER.debug("Deleting a answer entry with id: {}", id);

        Answer deleted = service.deleteById(id);
        LOGGER.debug("Deleted answer entry with information: {}", deleted);

        return deleted;
    }
    
    @RequestMapping(value = "/subjectiveanswer/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public SubjectiveAnswer deleteSubjectiveAnswerById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a answer entry with id: {}", id);

        SubjectiveAnswer deleted = subjectiveService.deleteById(id);
        LOGGER.debug("Deleted answer entry with information: {}", deleted);

        return deleted;
    }
    
    @RequestMapping(value = "/objectiveanswer/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectiveAnswer deleteObjectiveAnswerById(@PathVariable("id") Long id) throws  NotFoundException {
        LOGGER.debug("Deleting a answer entry with id: {}", id);

        ObjectiveAnswer deleted = objectiveService.deleteById(id);
        LOGGER.debug("Deleted answer entry with information: {}", deleted);

        return deleted;
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
    
    @RequestMapping(value = "/subjectiveanswers", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SubjectiveAnswer> findAllSubjectiveAnswer(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	 LOGGER.debug("Finding answer entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<SubjectiveAnswer> pageResult = subjectiveService.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/objectiveanswers", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<ObjectiveAnswer> findAllObjectiveAnswer(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	 LOGGER.debug("Finding answer entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<ObjectiveAnswer> pageResult = objectiveService.findAll(pageable);

         return new PageResource<>(pageResult,"page","size");
    }
     
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
    
    @RequestMapping(value = "/subjectiveanswers/{userid}/{questionid}", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<SubjectiveAnswer> findOtherPersonSubjectiveQuestions(@PathVariable("userid") Long userId,@PathVariable("questionid") Long questionId,@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
    	 LOGGER.debug("Finding subjective answer entry with id: {}" );
         sortBy = sortBy==null?"id": sortBy;
         Pageable pageable = new PageRequest(
                 page,size,new Sort(sortBy)
         );
         Page<SubjectiveAnswer> pageResult = subjectiveService.findOtherPersonAnswer(questionId,userId, pageable); 

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
    
    @RequestMapping(value = "/subjectiveanswer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SubjectiveAnswer  findSubjectiveAnswerById(@PathVariable("id") Long id) throws  NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        SubjectiveAnswer found = subjectiveService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }
    
    @RequestMapping(value = "/objectiveanswer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectiveAnswer  findObjectiveAnswerById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        ObjectiveAnswer found = objectiveService.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }

   

}
