package com.syzton.sunread.model.exam;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.syzton.sunread.model.common.AbstractEntity;


@Entity 
@Table(name = "question")  
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING, length = 30)   
public abstract class Question extends AbstractEntity {
	
	public static final int MAX_LENGTH_DESCRIPTION = 500;
	
	@Column(nullable=false,length= MAX_LENGTH_DESCRIPTION)
	private String topic;
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void setCreationTime(String creationTime) {
    	DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd");  
        //时间解析    
        DateTime dateTime2 = DateTime.parse(creationTime, format); 
    	this.creationTime = dateTime2;
    }
	
	

}
