package com.syzton.sunread.model.exam;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.SubjectiveQuestion.Builder;

@Entity
@DiscriminatorValue("objective")
public class ObjectiveQuestion extends Question {
	
//	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,optional=true)
//	@JoinColumn(name="book_id",nullable=false)
//	private Book book;
	
	@Column(name="book_id")
	private Long bookId;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH,CascadeType.REMOVE})
	@JoinColumn(name="question_id")  
	private Set<Option> options;
	
	
	@Column(name="objective_type",nullable=false)
	private QuestionType objectiveType;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)  
    @JoinColumn(name="correct_id")  
    private Option correctAnswer;
	
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public QuestionType getObjectiveType() {
		return objectiveType;
	}

	public void setObjectiveType(QuestionType objectiveType) {
		this.objectiveType = objectiveType;
	}

	
	
	public enum QuestionType {
		VERIFY,WORD,CAPACITY
	}

	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

		private ObjectiveQuestion built;

		public Builder() {
			built = new ObjectiveQuestion();
		}

		public ObjectiveQuestion build() {
			return built;
		}

	 
	}

	  
	
	public Set<Option> getOptions() {
		return options;
	}

	public Option getCorrectAnswer() {
		return this.correctAnswer;
	}
	
	public void setCorrectAnswer(Option option){
		this.correctAnswer = option;
	}
	
	public void setOptions(Set<Option> options) {
		this.options = options;
	}

	 

 
}
