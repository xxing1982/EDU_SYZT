package com.syzton.sunread.dto.exam;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Question;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,include=JsonTypeInfo.As.PROPERTY,property="typeName")
@JsonSubTypes({@JsonSubTypes.Type(value=ObjectiveQuestionDTO.class,name="objective"),@JsonSubTypes.Type(value=SubjectiveQuestionDTO.class,name="subjective")})
public abstract class QuestionDTO {
	private Long id;
	
	private String subject;
	
   
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public abstract Question OTD();   

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
