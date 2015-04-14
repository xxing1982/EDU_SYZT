package com.syzton.sunread.model.region;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

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
 */
@Entity
@Table(name="region")
public class Region extends AbstractEntity{
    
	public static final int MAX_LENGTH_DESCRIPTION = 500;
	public static final int MAX_LENGTH_AREA = 100;
	
    @Column(name = "province", nullable = true, length = MAX_LENGTH_AREA )
    private String province;
    
    @Column(name = "city", nullable = true, length = MAX_LENGTH_AREA)
    private String city;
    
    @Column(name = "district", nullable = true, length = MAX_LENGTH_AREA)
    private String district;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @JsonSerialize(using = DateSerializer.class)
	@Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="region")
    @Basic(fetch = FetchType.LAZY)
    private Set<Campus> compus= new HashSet<Campus>();
    
    public Region () {
		
	}
    
    public String generateAddress(){
    	return this.province + this.city + this.district;
    }

	public String getDescription() {
		return description;
	}

	public DateTime getModificationTime() {
		return modificationTime;
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
	
    public Set<Campus> getCampus() {
        return compus;
    }

    public void setSchools(Set<Campus> compus) {
        this.compus = compus;
    }
    
    
    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
        modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        modificationTime = DateTime.now();
    }

    
}
