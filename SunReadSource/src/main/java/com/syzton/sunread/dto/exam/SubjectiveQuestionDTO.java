package com.syzton.sunread.dto.exam;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syzton.sunread.model.exam.Question;

@JsonTypeName("subjective")
public class SubjectiveQuestionDTO extends QuestionDTO {

	@Override
	public Question OTD() {
		// TODO Auto-generated method stub
		return null;
	}

}
