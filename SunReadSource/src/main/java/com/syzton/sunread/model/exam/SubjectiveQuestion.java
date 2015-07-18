package com.syzton.sunread.model.exam;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Entity
@DiscriminatorValue("subjective")
public class SubjectiveQuestion extends Question {
	
	@Column(name="book_id")
	private Long bookId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="subjective_que_type",length=10,nullable=false)
	private SubjectiveQuestionType questionType;
	
	public enum SubjectiveQuestionType{FIRST,SECOND,THIRD,FOURTH,FIFTH}
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

		private SubjectiveQuestion built;

		public Builder() {
			built = new SubjectiveQuestion();
		}

		public SubjectiveQuestion build() {
			return built;
		}

	 
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
