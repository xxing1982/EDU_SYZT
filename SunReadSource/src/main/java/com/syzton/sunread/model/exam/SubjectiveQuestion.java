package com.syzton.sunread.model.exam;

import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.model.exam.ObjectiveAnswer.Builder;

public class SubjectiveQuestion extends Question {
	
	
	public static Builder getBuilder() {
        return new Builder();
    }
	
	public static class Builder {

		private SubjectiveQuestion built;

		public Builder() {
			built = new SubjectiveQuestion();
		}

		public SubjectiveQuestion build() {
			return built;
		}

	 
	}
	@Override
	public QuestionDTO createDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
