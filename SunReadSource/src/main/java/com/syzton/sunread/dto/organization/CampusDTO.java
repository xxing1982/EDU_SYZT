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
	
	private Long regionId;
	
	private String address;
	
	private String school;
	
	private int classNum;

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

	public Long getRegionId() {
		return regionId;
	}

	public Long setRegionId(Long region) {
		return this.regionId = region;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
}
