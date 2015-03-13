package com.syzton.sunread.model.exam;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.dto.exam.ObjectiveAnswerDTO;
import com.syzton.sunread.model.exam.SubjectiveAnswer.Builder;

@Entity
@DiscriminatorValue("objective")
public class ObjectiveAnswer extends Answer {
	@OneToOne(cascade=CascadeType.REFRESH)  
    @JoinColumn(name="option_id")  
	private Option option;
	
	@Override
	public void update(AnswerDTO updated) {
		
	}
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

		private ObjectiveAnswer built;

		public Builder() {
			built = new ObjectiveAnswer();
		}

		public ObjectiveAnswer build() {
			return built;
		}

	 
	}

	@Override
	public AnswerDTO createDTO() {
		ObjectiveAnswerDTO dto = new ObjectiveAnswerDTO();
		dto.setId(this.getId());
		if(option!=null){
			dto.setOptionDTO(option.createDTO());
		}
		return dto;
	}
	
	public boolean isCorrect(){
		if(this.option==null){
			return false;
		}
		
		return this.option.equals(((ObjectiveQuestion)this.question).getCorrectAnswer());
	}
}
