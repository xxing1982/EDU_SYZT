package com.syzton.sunread.dto.exam;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.ObjectiveAnswer;

@JsonTypeName("objective")
public class ObjectiveAnswerDTO extends AnswerDTO {
	
	private OptionDTO option;
 
	public void setOptionDTO(OptionDTO optionDTO){
		this.option = optionDTO;
	}
	
	public OptionDTO getOptionDTO(){
		return this.option;
	}
	
	@Override
	public Answer OTD() {
		Answer answer = new ObjectiveAnswer();
		
		return answer;
	}

}
