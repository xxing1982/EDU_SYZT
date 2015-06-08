package com.syzton.sunread.model.region;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.util.DateSerializer;

/**
 * @author Morgan-Leon
 * @Date 2015-3-23
 *
 * @author Jerry Zhang
 * @Date 2015-06-06
 * 
 */
@Entity
@Table(name="region")
public class Region extends AbstractEntity{
    
	public static final int MAX_LENGTH_DESCRIPTION = 500;
	public static final int MAX_LENGTH_AREA = 100;

	@Enumerated(EnumType.STRING)
	private RegionType regionType;

	private int areaCode;

	@Column(name = "name", nullable = false, length = MAX_LENGTH_AREA)
	private String name;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	@OrderColumn
    private Set<Region> subRegion= new HashSet<Region>();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Region> getSubRegion() {
		return subRegion;
	}

	public void setSubRegion(Set<Region> subRegion) {
		this.subRegion = subRegion;
	}
}
