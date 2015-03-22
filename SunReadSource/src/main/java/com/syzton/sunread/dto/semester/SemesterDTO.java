package com.syzton.sunread.dto.semester;
/*
 * @Author Morgan-Leon 
 * @Date 2015-3-22
 */
public class SemesterDTO {
	
	private Long id;
	
	private String description;
	
	private long startTime;
	
	private long endTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}	
	
	
}
