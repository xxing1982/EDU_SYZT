package com.syzton.sunread.dto.region;

import javax.validation.constraints.NotNull;

import com.syzton.sunread.model.region.RegionType;
import org.hibernate.validator.constraints.Length;

import com.syzton.sunread.model.region.Region;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Morgan-Leon
 * @Date 2015年3月23日
 * 
 */
public class RegionDTO {
	
	private long id;
	
    @Length(max = Region.MAX_LENGTH_DESCRIPTION)
	private String description;
	
	@NotNull
    @Length(max = Region.MAX_LENGTH_AREA)
    private String name;
    
    private RegionType regionType;

	private Set<RegionDTO> regionDTOSet = new HashSet<>();
	
    private int areaCode;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public RegionType getRegionType() {
		return regionType;
	}

	public void setRegionType(RegionType regionType) {
		this.regionType = regionType;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public Set<RegionDTO> getRegionDTOSet() {
		return regionDTOSet;
	}

	public void setRegionDTOSet(Set<RegionDTO> regionDTOSet) {
		this.regionDTOSet = regionDTOSet;
	}
}
