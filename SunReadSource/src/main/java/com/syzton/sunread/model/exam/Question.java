package com.syzton.sunread.model.exam;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.dto.exam.QuestionDTO;


@Entity 
@Table(name = "question")  
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING, length = 30)   
public abstract class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "creation_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationTime;

	@ManyToMany(mappedBy="questions",cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
	private Set<Exam> exams;
	
	public abstract QuestionDTO createDTO();

	public void update(QuestionDTO updated) {
		// TODO Auto-generated method stub
		
	}
}
