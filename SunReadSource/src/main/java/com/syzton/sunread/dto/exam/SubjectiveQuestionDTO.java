package com.syzton.sunread.dto.exam;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveQuestion;

@JsonTypeName("subjective")
public class SubjectiveQuestionDTO extends QuestionDTO {

	
	
	@Override
	public Question OTD() {
		Question question = SubjectiveQuestion.getBuilder().build();
		// TODO Auto-generated method stub
		return null;
	}

}
