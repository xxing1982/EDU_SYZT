package com.syzton.sunread.model.exam;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Entity
@DiscriminatorValue("capacity")
public class CapacityQuestion extends ObjectiveQuestion{
	
	public enum CapacityQuestionType{FIRST,SECOND,THIRD,FOURTH,FIFTH,SIXTH,SEVENTH,EIGHTTH}

	@Enumerated(EnumType.STRING)
	@Column(name="capacity_que_type",length=10,nullable=false)
	private CapacityQuestionType questionType;
	
	@Column(name="level")
	private int level;
	
	public CapacityQuestionType getQuestionType() {
		return questionType;
	}

	public int getLevel() {
		return level;
	}

	public void setQuestionType(CapacityQuestionType questionType) {
		this.questionType = questionType;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	
	

}
