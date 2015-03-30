package com.syzton.sunread.service.exam;

import java.util.List;
import java.util.Set;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.user.Student;

public interface ExamService {
	public Exam add(Exam added);

	public Exam deleteById(Long id)throws NotFoundException;

	public Page<Exam> findAll(Pageable pageable) throws NotFoundException;

	public Exam findById(Long id) throws NotFoundException;

	public List<ObjectiveQuestion> takeVerifyTest(Long bookId);

	public List<CapacityQuestion> takeCapacityTest(Long bookId);

	public List<SubjectiveQuestion> takeThinkTest(Long bookId);

	public Exam handInVerifyPaper(Exam added);

	public Exam handInCapacityTest(Exam added);

	public Exam handInThinkTest(Exam added);
	
	public List<Exam> getTodayVerifyTestStatus(Long bookId, Long studentId);
}
