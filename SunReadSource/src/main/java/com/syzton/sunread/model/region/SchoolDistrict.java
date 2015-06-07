/**
 * 
 */
package com.syzton.sunread.model.region;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syzton.sunread.dto.region.SchoolDistrictDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.organization.Campus;

/**
 * @author Morgan-Leon
 * @Date 2015年6月6日
 * 
 */
@Entity
@Table(name="school_district")
@JsonIgnoreProperties
public class SchoolDistrict extends AbstractEntity{
	public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    
    @Column(name ="name", nullable = false, unique = true, length = MAX_LENGTH_NAME)
    private String name; 
    
    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;
    
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @Basic(fetch = FetchType.EAGER)
    @JoinColumn(name = "region")
    private Region region;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy ="schoolDistrict")
    @Basic(fetch = FetchType.LAZY)
    private Set<Campus> campus = new HashSet<Campus>();


    public SchoolDistrict() {
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }
    
    public void setName(String name){
    	this.name = name;
    }

	public String getName() {
		return name;
	}
	
	private String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
    public static Builder getBuilder(String name, Region region) {
    	return new Builder(name, region);	
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
    
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

    public Set<Campus> getCampus() {
        return campus;
    }
    
    public void setGrades(Set<Campus> campus) {
        this.campus = campus;
    }

	public void update(String name) {
		// TODO Auto-generated method stub
		this.name = name;	
	}
	
    public static class Builder {

        private SchoolDistrict built;

        public Builder() {
            // TODO Auto-generated constructor stub
            built = new SchoolDistrict();
        }

        public SchoolDistrict build() {
            return built;
        }

        public Builder(String name,Region region) {
            built = new SchoolDistrict();
            built.name = name;
            built.region = region;
        }

        public Builder Campus(Set<Campus> campus) {
            built.campus = campus;
            return this;
        }

		public Builder description(String description) {
            built.description = description;
            return this;
        }
				
	}
    
    public SchoolDistrictDTO createDTO(SchoolDistrict model) {
        SchoolDistrictDTO dto = new SchoolDistrictDTO();
        dto.setId(model.id);
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setRegionId(model.region.getId());
        dto.setAddress(model.region.generateAddress());
        return dto;
    }


}
