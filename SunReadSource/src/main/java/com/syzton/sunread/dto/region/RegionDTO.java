/**
 * 
 */
package com.syzton.sunread.dto.region;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.syzton.sunread.model.region.Region;

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
    private String province;
    
	@NotNull
    @Length(max = Region.MAX_LENGTH_AREA)
    private String city;
	
	private Set<String> schools = new HashSet<String>();
    
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the schools
	 */
	public Set<String> getSchools() {
		return schools;
	}

	/**
	 * @param schools the schools to set
	 */
	public void setSchools(Set<String> schools) {
		this.schools = schools;
	}

	@NotNull
    @Length(max = Region.MAX_LENGTH_AREA)
    private String district;
	
	
}
