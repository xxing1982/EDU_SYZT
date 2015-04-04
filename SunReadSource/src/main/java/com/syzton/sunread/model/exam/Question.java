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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.common.AbstractEntity;


@Entity 
@Table(name = "question")  
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING, length = 30)   
public abstract class Question extends AbstractEntity {
	
	public static final int MAX_LENGTH_DESCRIPTION = 500;
	
	@Column(nullable=false,length= MAX_LENGTH_DESCRIPTION)
	private String topic;
	


//	@ManyToMany(mappedBy="questions",cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
//	private Set<Exam> exams;
//	
//
//	public Set<Exam> getExams() {
//		return exams;
//	}
//
//	public void setExams(Set<Exam> exams) {
//		this.exams = exams;
//	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
