package com.syzton.sunread.controller.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.exception.common.TodayVerifyTimesOverException;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.exception.exam.HaveVerifiedBookException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.coinhistory.CoinHistory.CoinFrom;
import com.syzton.sunread.model.coinhistory.CoinHistory.CoinType;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.model.pointhistory.PointHistory.PointFrom;
import com.syzton.sunread.model.pointhistory.PointHistory.PointType;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.service.book.TestPassService;
import com.syzton.sunread.service.coinhistory.CoinHistoryService;
import com.syzton.sunread.service.exam.AnswerService;
import com.syzton.sunread.service.exam.ExamService;
import com.syzton.sunread.service.exam.ObjectiveAnswerService;
import com.syzton.sunread.service.exam.SubjectiveAnswerService;
import com.syzton.sunread.service.pointhistory.PointHistoryService;

@Controller
@RequestMapping(value = "/api")
public class ExamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    private ExamService service;
    
    private TestPassService testPassService;
    
    private CoinHistoryService coinService;
    
    private PointHistoryService pointService;
    
     
    @Autowired
    public ExamController(ExamService service,TestPassService tService,CoinHistoryService coinService,PointHistoryService pointService) {
        this.service = service;
        this.testPassService = tService;
        this.coinService = coinService;
        this.pointService = pointService;
    }

    @RequestMapping(value = "/exam", method = RequestMethod.POST)
    @ResponseBody
    public Exam add(@Valid @RequestBody Exam dto) {
        LOGGER.debug("Adding a new exam entry with information: {}", dto);

        Exam added = service.add(dto);
        LOGGER.debug("Added a exam entry with information: {}", added);

       return added;
    }
    
    @RequestMapping(value = "/exam/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Exam deleteById(@PathVariable("id") Long id) throws  NotFoundException {
        LOGGER.debug("Deleting a exam entry with id: {}", id);

        Exam deleted = service.deleteById(id);
        LOGGER.debug("Deleted exam entry with information: {}", deleted);

        return deleted;
    }

    @RequestMapping(value = "/exam", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Exam> findAll(@RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy) throws NotFoundException {
        LOGGER.debug("Finding all exam entries.");
        sortBy = sortBy==null?"id": sortBy;
        Pageable pageable = new PageRequest(
                page,size,new Sort(sortBy)
        );
        Page<Exam> pageResult = service.findAll(pageable);
        LOGGER.debug("Found {} exam entries.", pageResult.getTotalElements());

        return new PageResource<>(pageResult,"page","size");
    }
    
    @RequestMapping(value = "/exam/verifypaper/{studentid}/{bookid}", method = RequestMethod.GET)
    @ResponseBody
    public List<ObjectiveQuestion> createVerifyPaper(@PathVariable("studentid") Long studentId,@PathVariable("bookid") Long bookId) throws NotFoundException {
        LOGGER.debug("Finding all exam entries.");
        if(service.isPassVerifyTest(bookId, studentId)){
        	throw new HaveVerifiedBookException("Student "+studentId+" have verified the book " +bookId);
        }
        List<Exam> list = service.getTodayVerifyTestStatus(bookId, studentId);
        if(list.size()>=2){
        	throw new TodayVerifyTimesOverException("Student{"+studentId+"} verify test with book{"+bookId+"} greater than twice, system ignore this verify test request.");
        }
        List<ObjectiveQuestion> questions = service.takeVerifyTest(bookId);
        LOGGER.debug("Found {} exam entries.", questions.size());

        return questions;
    }
    
    

    @RequestMapping(value = "/exam/capacitypaper/", method = RequestMethod.GET)
    @ResponseBody
    public List<CapacityQuestion> createCapacityPaper(@PathVariable("bookid") Long bookId) throws NotFoundException {
        LOGGER.debug("Finding all todo entries.");
       
        List<CapacityQuestion> questions = service.takeCapacityTest();
        LOGGER.debug("Found {} exam entries.", questions.size());

        return questions;
    }
    
    @RequestMapping(value = "/exam/thinkpaper/{bookid}", method = RequestMethod.GET)
    @ResponseBody
    public List<SubjectiveQuestion> createThinkPaper(@PathVariable("bookid") Long bookId) throws NotFoundException {
        LOGGER.debug("Finding all exam entries.");
       
        List<SubjectiveQuestion> questions = service.takeThinkTest(bookId);
        LOGGER.debug("Found {} exam entries.", questions.size());

        return questions;
    }
    
    @RequestMapping(value = "/exam/verifypaper", method = RequestMethod.POST)
    @ResponseBody
    public Exam handInVerifyPaper(@Valid @RequestBody Exam exam) throws NotFoundException {
        LOGGER.debug("hand in exam entrie.");
        long studentId = exam.getStudent().getId();
        long bookId = exam.getBook().getId();
        if(service.isPassVerifyTest(bookId, studentId)){
        	throw new HaveVerifiedBookException("Student "+studentId+" have verified the book " +bookId);
        }
        List<Exam> list = service.getTodayVerifyTestStatus(studentId, bookId);
        if(list.size()>=2){
        	throw new TodayVerifyTimesOverException("Student{"+studentId+"} verify test with book{"+bookId+"} greater than twice, system ignore this verify test request.");
        }
        Exam examResult = service.handInVerifyPaper(exam);
        if(examResult.isPass()){
        	testPassService.hotBookUpdate(bookId, studentId);
        	CoinHistory coinHistory = new CoinHistory();
        	coinHistory.setCoinFrom(CoinFrom.FROM_VERIFY_TEST);
        	coinHistory.setCoinType(CoinType.IN);
        	User user = new Student();
        	user.setId(studentId);
        	coinHistory.setNum(2);
        	coinHistory.setUser(user);
        	coinService.add(coinHistory);
        	
        	PointHistory pointHistory = new PointHistory();
        	pointHistory.setPointFrom(PointFrom.FROM_VERIFY_TEST);
        	pointHistory.setPointType(PointType.IN);
        	pointHistory.setNum(2);
        	pointHistory.setUser(user);
        	pointService.add(pointHistory);
        }
        LOGGER.debug("return a exam entry result with information: {}", exam);

        return examResult;
    }
    
    @RequestMapping(value = "/exam/capacitypaper", method = RequestMethod.POST)
    @ResponseBody
    public Exam handInThinkPaper(@Valid @RequestBody Exam exam) throws NotFoundException {
        LOGGER.debug("hand in exam entrie.");
       
        Exam examResult = service.handInThinkTest(exam);
        LOGGER.debug("return a exam entry result with information: {}", exam);

        return examResult;
    }
    
    @RequestMapping(value = "/exam/thinkpaper", method = RequestMethod.POST)
    @ResponseBody
    public Exam handInCapacityPaper(@Valid @RequestBody Exam exam) throws NotFoundException {
        LOGGER.debug("hand in exam entrie.");
       
        Exam examResult = service.handInCapacityTest(exam);
        LOGGER.debug("return a exam entry result with information: {}", exam);

        return examResult;
    }
    

    @RequestMapping(value = "/exam/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Exam findById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Finding to-do entry with id: {}", id);

        Exam found = service.findById(id);
        LOGGER.debug("Found to-do entry with information: {}", found);

        return found;
    }
    

//    @RequestMapping(value = "/exam/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public Exam update(@Valid @RequestBody Exam exam, @PathVariable("id") Long todoId) throws AnswerNotFoundException {
//        LOGGER.debug("Updating a to-do entry with information: {}", dto);
//
//        Exam updated = service.update(exam);
//        LOGGER.debug("Updated the information of a to-entry to: {}", updated);
//
//        return updated;
//    }
}
