package com.syzton.sunread.dto.exam;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion.QuestionType;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;

public class ObjectQuestionWithName {
	private long id;
	private String topic;
	

	private Long bookId;
	
	private List<Option> options;
	
	private QuestionType objectiveType;
	
    private Option correctAnswer;
	
	private String bookName;

	public ObjectQuestionWithName(ObjectiveQuestion sq){
		this.id = sq.getId();
		this.bookId = sq.getBookId();
		this.objectiveType = sq.getObjectiveType();
		this.topic = sq.getTopic();
		this.options = sq.getOptions();
		this.correctAnswer = sq.getCorrectAnswer();
		
		this.bookName="";
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public QuestionType getObjectiveType() {
		return objectiveType;
	}

	public void setObjectiveType(QuestionType objectiveType) {
		this.objectiveType = objectiveType;
	}

	public Option getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(Option correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

}
