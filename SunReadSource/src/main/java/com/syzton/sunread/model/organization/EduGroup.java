package com.syzton.sunread.model.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syzton.sunread.dto.organization.EduGroupDTO;
import com.syzton.sunread.model.common.AbstractEntity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Entity
@Table(name="edu_group")
@JsonIgnoreProperties
public class EduGroup extends AbstractEntity{


    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    
    @Column(name ="name", nullable = false, length = MAX_LENGTH_NAME)
    private String name; 

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

	@Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

	//cascade = CascadeType.ALL,mappedBy = "note"
    @OneToMany(cascade = CascadeType.ALL, mappedBy="eduGroup")
    @Basic(fetch = FetchType.LAZY)
    private Set<School> schools = new HashSet<School>();


    public EduGroup() {
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    public static Builder getBuilder(String name) {
    	return new Builder(name);
		
	}
    
    public String getDescription() {
		return description;
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

    public Set<School> getSchools() {
        return schools;
    }

    public void setSchools(Set<School> schools) {
        this.schools = schools;
    }
    
	public void update(String name) {
		// TODO Auto-generated method stub
		this.name = name;			
	}

    public static class Builder {

        private EduGroup built;

        public EduGroup build() {
            return built;
        }

        public Builder(String name) {
        	built = new EduGroup();
            built.name = name;
        }

        public  Builder schools(Set<School> schools)
        {
            built.schools = schools;
            return this;
        }
        
		public Builder description(String description) {
            built.description = description;
            return this;
        }
    }
        
        public EduGroupDTO createDTO() {
            EduGroupDTO dto = new EduGroupDTO();
            dto.setId(this.id);
            dto.setName(this.getName());
            dto.setDescription(this.getDescription());
            return dto;
    }
        public EduGroupDTO createDTO(EduGroup model) {
            EduGroupDTO dto = new EduGroupDTO();
            dto.setId(model.id);
            dto.setName(model.getName());
            dto.setDescription(model.getDescription());
            return dto;
    }

}
