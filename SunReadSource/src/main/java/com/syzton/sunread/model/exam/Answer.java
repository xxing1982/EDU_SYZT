package com.syzton.sunread.model.exam;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.syzton.sunread.model.common.AbstractEntity;
 

@Entity  
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "answer_type", discriminatorType = DiscriminatorType.STRING, length = 30)   
public abstract class Answer extends AbstractEntity {
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="question_id")
	protected Question question;
	
	@Column(name="student_id")
	protected Long studentId;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
	
}
