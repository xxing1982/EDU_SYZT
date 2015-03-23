package com.syzton.sunread.model.semester;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.semester.SemesterDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;

/*
 * @Date 2015-3-22
 * @Author Morgan-Leon 
 */
@Entity
@Table(name="semester")
public class Semester extends AbstractEntity{
	
    public static final int MAX_LENGTH_DESCRIPTION = 500;
    
    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @JsonSerialize(using = DateSerializer.class)
    @Column(name ="start_time",nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startTime;
    
    @JsonSerialize(using = DateSerializer.class)
    @Column(name ="end_time",nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endTime;
    
    public Semester() {

    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }


	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {

		this.endTime = endTime;
	}
    

}
