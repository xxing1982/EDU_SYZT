package com.syzton.sunread.dto.exam;

import java.util.List;

import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;

public class ObjectiveQuestionDTO extends QuestionDTO {
	
	
	private List<OptionDTO> options;
	
	

	public List<OptionDTO> getOptions() {
		return options;
	}

	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}

	@Override
	public Question OTD() {
		Question question = ObjectiveQuestion.getBuilder().build();
		return question;
	}

	
}
