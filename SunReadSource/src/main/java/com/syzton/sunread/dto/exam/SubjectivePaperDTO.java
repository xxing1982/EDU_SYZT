package com.syzton.sunread.dto.exam;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveQuestion;

public class SubjectivePaperDTO {
	private Set<SubjectiveQuestion> questions;

	private Long bookId;
 
	private Long studentId;

	private ExamType examType;
	 
	private Set<SubjectiveAnswerDTO> answers;
	
 

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
		for(SubjectiveAnswerDTO answer : answers){
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
	
	public Set<SubjectiveQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<SubjectiveQuestion> questions) {
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

	public Set<SubjectiveAnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<SubjectiveAnswerDTO> answers) {
		this.answers = answers;
	}
}
