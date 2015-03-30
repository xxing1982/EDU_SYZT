package com.syzton.sunread.model.exam;

import java.util.Set;

import javax.persistence.CascadeType;
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
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="book_id",nullable=false)
	private Book book;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE, CascadeType.MERGE,CascadeType.REFRESH })
	@JoinColumn(name = "question_id")
	private Set<Option> options;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)  
    @JoinColumn(name="correct_id")  
    private Option correctAnswer;
	
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
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

	@Override
	public QuestionDTO createDTO() {
		// TODO Auto-generated method stub
		return null;
	}  
	
	public Set<Option> getOptions() {
		return options;
	}

	public Option getCorrectAnswer() {
		return correctAnswer;
	}

 
}
