package com.syzton.sunread.model.exam;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.model.exam.SubjectiveQuestion.Builder;

@Entity
@DiscriminatorValue("objective")
public class ObjectiveQuestion extends Question {

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE, CascadeType.MERGE,CascadeType.REFRESH })
	@JoinColumn(name = "question_id")
	private Set<Option> options;
	
	@OneToOne(cascade=CascadeType.ALL)  
    @JoinColumn(name="correct_id")  
    private Option correctAnswer;
	
	
	
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
