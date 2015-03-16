package com.syzton.sunread.model.exam;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.model.exam.Exam.ExamType;
import com.syzton.sunread.model.exam.ObjectiveAnswer.Builder;
@Entity
@DiscriminatorValue("subjective")
public class SubjectiveQuestion extends Question {
	
	@Enumerated(EnumType.STRING)
	@Column(name="subjective_que_type",length=10,nullable=false)
	private SubjectiveQuestionType questionType;
	
	//TODO.. SubjectiveQuestion Type Name
	public enum SubjectiveQuestionType{FIRST,SECOND,THIRD,FOURTH,FIFTH}
	
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
