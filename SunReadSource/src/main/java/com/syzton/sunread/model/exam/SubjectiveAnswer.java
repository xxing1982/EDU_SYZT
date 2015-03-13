package com.syzton.sunread.model.exam;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.dto.exam.SubjectiveAnswerDTO;

@Entity
@DiscriminatorValue("subjective")
public class SubjectiveAnswer extends Answer {
	
	@Column
	private String content;
	
	@Column
	private String comment;
	
	@Column(name = "creation_time", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime commentTime;
	 
	public String getContent() {
		return content;
	}

	public String getComment() {
		return comment;
	}

	public DateTime getCommentTime() {
		return commentTime;
	}

	@Override
	public void update(AnswerDTO updated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AnswerDTO createDTO() {
		SubjectiveAnswerDTO dto = new SubjectiveAnswerDTO();
		dto.setId(this.getId());
		dto.setComment(this.getComment());
		dto.setCommentTime(this.getCommentTime());
		if(this.question!=null){
			dto.setQuestionDTO(this.getQuestion().createDTO());
		}
		return dto;
	}
}
