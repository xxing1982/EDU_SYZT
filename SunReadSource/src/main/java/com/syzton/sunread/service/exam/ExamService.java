package com.syzton.sunread.service.exam;

import java.util.List;
import java.util.Map;

import javassist.NotFoundException;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.exam.VerifyExamPassDTO;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.CapacityQuestion.CapacityQuestionType;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.Exam.ExamType;

public interface ExamService {
	public Exam add(Exam added);

	public Exam deleteById(Long id)throws NotFoundException;

	public Page<Exam> findAll(Pageable pageable) throws NotFoundException;

	public Exam findById(Long id) throws NotFoundException;

	public List<ObjectiveQuestion> takeVerifyTest(Long bookId);
	
	public List<ObjectiveQuestion> takeWordTest(Long bookId);
	
	public List<ObjectiveQuestion> takeSpeedTest();

	public List<CapacityQuestion> takeCapacityTest(int level);

	public List<SubjectiveQuestion> takeThinkTest(Long bookId);

	public Exam handInVerifyPaper(Exam added);
	
	public Exam handInWordPaper(Exam added);

	public Exam handInCapacityTest(Exam added);

	public Exam handInThinkTest(Exam added);
	
	public Exam handInSpeedTest(Exam added);
	
	public List<Exam> getTodayVerifyTestStatus(Long bookId, Long studentId);
	
	public boolean isPassVerifyTest(Long bookId,Long studentId);
	
	public VerifyExamPassDTO findAllByExamTypeAndPassStatus(Long studentId,ExamType type) throws NotFoundException;
	
	public VerifyExamPassDTO findAllByExamType(Long studentId,ExamType type) throws NotFoundException;
	
	public int findPassVerifyExamPassRate(Long studentId, DateTime from,DateTime to);
	
	public int findFirstPassRate(Long studentId, DateTime from,DateTime to);
	
	public int findSecondPassRate(Long studentId, DateTime from,DateTime to);
	
	public Map<CapacityQuestionType, Integer> getStudentCapacityStatus(Long student,DateTime from,DateTime to);
}
