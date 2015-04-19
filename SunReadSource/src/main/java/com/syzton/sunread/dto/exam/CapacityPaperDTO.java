package com.syzton.sunread.dto.exam;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.Exam.ExamType;

public class CapacityPaperDTO {
	private Set<CapacityQuestion> questions;
 
	private Long studentId;

	private ExamType examType;
	 
	private Set<CapacityAnswerDTO> answers;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public Exam fromOTD(){
		Exam exam = new Exam();
		Set<Question>set = new HashSet<Question>();
		set.addAll(questions);
		exam.setQuestions(set);
		Set<Answer> answerSet = new HashSet<Answer>();
		for(CapacityAnswerDTO answer : answers){
			answerSet.add(answer.FromOTD());
		}
		exam.setAnswers(answerSet);
		exam.setExamType(examType);
		exam.setStudentId(studentId);
		exam.setQuestionNum(set.size());
		return exam;
	}
	
	public Set<CapacityQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<CapacityQuestion> questions) {
		this.questions = questions;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public Set<CapacityAnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<CapacityAnswerDTO> answers) {
		this.answers = answers;
	}
}
