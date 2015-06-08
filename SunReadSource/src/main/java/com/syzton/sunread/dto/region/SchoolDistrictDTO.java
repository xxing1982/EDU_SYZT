package com.syzton.sunread.dto.region;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.syzton.sunread.model.organization.EduGroup;

public class SchoolDistrictDTO {
	private Long id;
    
	@Length(max = EduGroup.MAX_LENGTH_DESCRIPTION)
    private String description;
	
	@NotNull
	@Length(max = EduGroup.MAX_LENGTH_NAME)
	private String name;
	
	private Long regionId;
	
	private String address;
	
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
	
	public Long getRegionId() {
		return regionId;
	}

	public Long setRegionId(Long region) {
		return this.regionId = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
