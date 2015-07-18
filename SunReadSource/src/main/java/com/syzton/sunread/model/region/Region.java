package com.syzton.sunread.model.region;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.syzton.sunread.model.common.AbstractEntity;

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


	@ManyToOne
	@JoinColumn(name="parent_id")
//	@JsonManagedReference
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	public Region parent;

	@Column(name = "name", nullable = false, length = MAX_LENGTH_AREA)
	private String name;

	@OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
	@OrderColumn
//	@JsonBackReference
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	private Set<Region> subRegion= new HashSet<>();

	public RegionType getRegionType() {
		return regionType;
	}

	public void setRegionType(RegionType regionType) {
		this.regionType = regionType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Region> getSubRegion() {
		return subRegion;
	}

	public void setSubRegion(Set<Region> subRegion) {
		this.subRegion = subRegion;
	}

	public Region getParent() {
		return parent;
	}

	public void setParent(Region parent) {
		this.parent = parent;
	}

}
