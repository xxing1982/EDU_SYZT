package com.syzton.sunread.model.exam;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.model.common.AbstractEntity;

@Entity
@Table(name = "capacity_quesiton")
public class CapacityQuestion extends AbstractEntity{
	
	public enum CapacityQuestionType{FIRST,SECOND,THIRD,FOURTH,FIFTH}
	
	@ManyToMany(mappedBy="questions",cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
	private Set<Exam> exams;
	

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE, CascadeType.MERGE,CascadeType.REFRESH })
	@JoinColumn(name = "question_id")
	private Set<Option> options;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)  
    @JoinColumn(name="correct_id")  
    private Option correctAnswer;
	
	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	
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


 
}
