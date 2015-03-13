package com.syzton.sunread.dto.exam;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.Question;

@JsonTypeName("objective")
public class ObjectiveQuestionDTO extends QuestionDTO {
	
	private OptionDTO correctAnswer;
	
	private List<OptionDTO> options;
	
	public OptionDTO getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(OptionDTO correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public List<OptionDTO> getOptions() {
		return options;
	}

	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}

	@Override
	public Question OTD() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
