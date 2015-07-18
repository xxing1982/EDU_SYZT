package com.syzton.sunread.dto.organization;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.syzton.sunread.model.organization.Clazz;

public class ClazzDTO {
	private Long id;
    
	@Length(max = Clazz.MAX_LENGTH_DESCRIPTION)
    private String description;
	
	@NotNull
	@Length(max = Clazz.MAX_LENGTH_NAME)
	private String name;
	
	@NotNull
	private int grade;
	
	private String campusName;

	private Set<CategoryCountDTO> categoryCountDTOs = new HashSet();

	public Set<CategoryCountDTO> getCategoryCountDTOs() {
		return categoryCountDTOs;
	}

	public void setCategoryCountDTOs(Set<CategoryCountDTO> categoryCountDTOs) {
		this.categoryCountDTOs = categoryCountDTOs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
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
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
