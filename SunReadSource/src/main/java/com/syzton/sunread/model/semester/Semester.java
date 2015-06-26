package com.syzton.sunread.model.semester;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.semester.SemesterDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.util.DateSerializer;

/*
 * @Date 2015-3-22
 * @Author Morgan-Leon 
 */
@Entity
@Table(name="semester")
public class Semester extends AbstractEntity{
	
    public static final int MAX_LENGTH_DESCRIPTION = 500;
    
    private String semester;
    
    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name = "campus")
    private Campus campus;
    
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
     
    public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
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
    
	public SemesterDTO createDTO() {
		SemesterDTO dto = new SemesterDTO();
		dto.setCampusId(campus.getId());
		dto.setCampusName(campus.getName());
		dto.setDescription(description);
		dto.setSemester(semester);
		dto.setStartTime(startTime.getMillis());
		dto.setEndTime(endTime.getMillis());
		return dto;
			
	}

}
