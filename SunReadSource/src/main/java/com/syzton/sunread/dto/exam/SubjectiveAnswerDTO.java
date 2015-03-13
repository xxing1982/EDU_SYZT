package com.syzton.sunread.dto.exam;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;

@JsonTypeName("subjective")
public class SubjectiveAnswerDTO extends AnswerDTO {
	
	private String content;
	
	private String comment;
	
	private QuestionDTO questionDTO;
	
	private DateTime commentTime; 
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public DateTime getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(DateTime commentTime) {
		this.commentTime = commentTime;
	}

	public QuestionDTO getQuestionDTO() {
		return questionDTO;
	}

	public void setQuestionDTO(QuestionDTO questionDTO) {
		this.questionDTO = questionDTO;
	}

	@Override
	public Answer OTD() {
		Answer answer = SubjectiveAnswer.getBuilder().build();
		return answer;
	}

}
