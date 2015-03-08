package com.syzton.sunread.model.answer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syzton.sunread.model.question.Question;

@Entity 
@Table(name = "answer")  
@Inheritance(strategy = InheritanceType.JOINED) 
public class Answer {
	
	@Id   
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private long id;
	
	@ManyToOne
	private Question question;
	
}
