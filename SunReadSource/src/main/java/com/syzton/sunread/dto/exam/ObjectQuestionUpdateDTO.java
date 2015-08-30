package com.syzton.sunread.dto.exam;

import java.util.List;

import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.ObjectiveQuestion.QuestionType;

public class ObjectQuestionUpdateDTO {
	
	private Long id;
	
	private String topic;
	
	private Long bookId;
	
	private List<Option> options;
	
	private QuestionType objectiveType;
	
    private Option correctAnswer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
