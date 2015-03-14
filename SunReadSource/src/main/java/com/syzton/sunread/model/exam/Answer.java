package com.syzton.sunread.model.exam;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syzton.sunread.dto.exam.AnswerDTO;
 

@Entity  
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "answer_type", discriminatorType = DiscriminatorType.STRING, length = 30)   
public abstract class Answer {
	
	@Id   
	@GeneratedValue(strategy = GenerationType.AUTO)  
	protected long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="question_id")
	protected Question question;
	
	public long getId() {
		return id;
	}

	public Question getQuestion() {
		return question;
	}
	
	public abstract void  update(AnswerDTO updated);
	
	public abstract AnswerDTO createDTO();
	
}
