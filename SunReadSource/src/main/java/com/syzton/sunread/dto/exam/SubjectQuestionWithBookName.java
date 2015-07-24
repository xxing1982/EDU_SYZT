package com.syzton.sunread.dto.exam;


import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;

public class SubjectQuestionWithBookName{
	
	private long id;
	private String topic;
	

	private Long bookId;
	
	private SubjectiveQuestionType questionType;
	
	private String bookName;

	public SubjectQuestionWithBookName(SubjectiveQuestion sq){
		this.id = sq.getId();
		this.bookId = sq.getBookId();
		this.questionType = sq.getQuestionType();
		this.topic = sq.getTopic();
		this.bookName="";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public SubjectiveQuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(SubjectiveQuestionType questionType) {
		this.questionType = questionType;
	}
}
