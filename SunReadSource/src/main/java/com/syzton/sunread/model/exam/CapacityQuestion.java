package com.syzton.sunread.model.exam;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;

@Entity
@DiscriminatorValue("capacity")
public class CapacityQuestion extends Question{
	
	public enum CapacityQuestionType{FIRST,SECOND,THIRD,FOURTH,FIFTH}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE, CascadeType.MERGE,CascadeType.REFRESH })
	@JoinColumn(name = "question_id")
	private Set<Option> options;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)  
    @JoinColumn(name="correct_id")  
    private Option correctAnswer;

	@Enumerated(EnumType.STRING)
	@Column(name="subjective_que_type",length=10,nullable=false)
	private CapacityQuestionType questionType;
	
	@Column(name="level")
	private int level;
	
	public Set<Option> getOptions() {
		return options;
	}

	public void setOptions(Set<Option> options) {
		this.options = options;
	}

	public Option getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(Option correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public QuestionDTO createDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
