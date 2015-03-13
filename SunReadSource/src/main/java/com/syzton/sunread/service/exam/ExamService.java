package com.syzton.sunread.service.exam;

import java.util.List;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.exception.answer.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveQuestion;

public interface ExamService {
	public Exam add(ExamDTO added);

	public Exam deleteById(Long id);

	public List<Exam> findAll();

	public Exam findById(Long id);

	public Exam update(ExamDTO updated);

	// exam

	public List<Question> takeVerifyTest(Long bookId);

	public List<Question> takeAbilityTest(Long bookId);

	public List<Question> takeThinkTest(Long bookId);

	public int handInVerifyPaper();

	public int handInAbilityTest();

	public boolean handInThinkTest();

	// question
	public boolean addQuestion();

	public boolean batchAddQuestion();

	public boolean updateSubjectiveQuestion();

	public boolean updateObjectiveQuestion();

	public boolean deleteQuestion();

}
