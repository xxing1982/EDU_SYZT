package com.syzton.sunread.model.exam;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.dto.exam.ObjectiveAnswerDTO;

@Entity
@DiscriminatorValue("objective")
public class ObjectiveAnswer extends Answer {
	
	private Option option;
	
	@Override
	public void update(AnswerDTO updated) {
		
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
