package com.syzton.sunread.dto.exam;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;

public class VerifyPaperExamDTO {

	private Set<ObjectiveQuestion> questions;

	private Long bookId;
 
	private Long studentId;

	private ExamType examType;
	 
	private Set<ObjectiveAnswerDTO> answers;

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
		for(ObjectiveAnswerDTO answer : answers){
			answerSet.add(answer.FromOTD());
		}
		exam.setAnswers(answerSet);
		Book book = new Book();
		book.setId(bookId);
		exam.setBook(book);
		exam.setExamType(examType);
		exam.setStudentId(studentId);
		exam.setQuestionNum(set.size());
		return exam;
	}
	
	public Set<ObjectiveQuestion> getQuestions() {
		return questions;
	}


	public void setQuestions(Set<ObjectiveQuestion> questions) {
		this.questions = questions;
	}


	public Long getBookId() {
		return bookId;
	}


	public void setBookId(Long bookId) {
		this.bookId = bookId;
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

	public Set<ObjectiveAnswerDTO> getAnswers() {
		return answers;
	}


	public void setAnswers(Set<ObjectiveAnswerDTO> answers) {
		this.answers = answers;
	}

	 
}
