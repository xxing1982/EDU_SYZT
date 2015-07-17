package com.syzton.sunread.dto.exam;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.syzton.sunread.model.exam.Answer;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,include=JsonTypeInfo.As.PROPERTY,property="typeName")
@JsonSubTypes({@JsonSubTypes.Type(value=ObjectiveAnswerDTO.class,name="objective"),@JsonSubTypes.Type(value=SubjectiveAnswerDTO.class,name="subjective")})
public abstract class AnswerDTO {
	private Long id;
	
	private QuestionDTO questionDTO;
	
    public QuestionDTO getQuestionDTO() {
		return questionDTO;
	}

	public void setQuestionId(QuestionDTO questionDTO) {
		this.questionDTO = questionDTO;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract Answer OTD();   

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
