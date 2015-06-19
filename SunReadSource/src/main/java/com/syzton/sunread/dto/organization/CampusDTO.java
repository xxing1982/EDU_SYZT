package com.syzton.sunread.dto.organization;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.syzton.sunread.model.organization.Campus;

/**
 * @author Morgan-Leon
 * @Date 2015年4月6日
 * 
 */
public class CampusDTO {
	
	private Long id;
    
	@Length(max = Campus.MAX_LENGTH_DESCRIPTION)
    private String description;
	
	@NotNull
	@Length(max = Campus.MAX_LENGTH_NAME)
	private String name;
	
	@Length(max = Campus.MAX_LENGTH_HEADMASTER)
    private String headmaster;
	
	private long regionId;
	
	private String regionName;
	
	private int classNum;

	private String wish;
	
	private int noteScore = Campus.DEFAULT_NOTE_SCORE;
	
	private Long schoolDistrictId;
	
	private String schoolDistrictName ;
	
	private Long eduGroupId;
	
	private String eduGroupName;
	
	
	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getNoteScore() {
		return noteScore;
	}

	public void setNoteScore(int noteScore) {
		this.noteScore = noteScore;
	}

	public String getWish() {
		return wish;
	}

	public void setWish(String wish) {
		this.wish = wish;
	}
	
	public Long getSchoolDistrictId() {
		return schoolDistrictId;
	}

	public void setSchoolDistrictId(Long schoolDistrictId) {
		this.schoolDistrictId = schoolDistrictId;
	}

	public String getSchoolDistrictName() {
		return schoolDistrictName;
	}

	public void setSchoolDistrictName(String schoolDistrictName) {
		this.schoolDistrictName = schoolDistrictName;
	}

	public Long getEduGroupId() {
		return eduGroupId;
	}

	public void setEduGroupId(Long eduGroupId) {
		this.eduGroupId = eduGroupId;
	}

	public String getEduGroupName() {
		return eduGroupName;
	}

	public void setEduGroupName(String eduGroupName) {
		this.eduGroupName = eduGroupName;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadmaster() {
		return headmaster;
	}

	public void setHeadmaster(String headmaster) {
		this.headmaster = headmaster;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}


	
	
	
	
}
