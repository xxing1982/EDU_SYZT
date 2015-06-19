package com.syzton.sunread.dto.semester;

import javax.validation.constraints.NotNull;

/*
 * @Author Morgan-Leon 
 * @Date 2015-3-22
 */
public class SemesterDTO {
	
	private long id;
	
	private String semester;
	
	private String description;
	
	private long campusId;
	
	private String campusName;
	
	@NotNull
	private Long startTime;
	
	@NotNull
	private Long endTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	

	public long getCampusId() {
		return campusId;
	}

	public void setCampusId(long campusId) {
		this.campusId = campusId;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}	
	
	
}
