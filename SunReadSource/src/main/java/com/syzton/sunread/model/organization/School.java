package com.syzton.sunread.model.organization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syzton.sunread.dto.organization.SchoolDTO;
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
@Table(name="school")
@JsonIgnoreProperties
public class School extends AbstractEntity{


    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    
    @Column(name ="name", nullable = false, unique = true, length = MAX_LENGTH_NAME)
    private String name; 
    
    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_group")
    private EduGroup eduGroup;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy ="school")
    @Basic(fetch = FetchType.LAZY)
    private Set<Campus> compus = new HashSet<>();


    public School() {
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }


	public String getName() {
		return name;
	}
	
	private String getDescription() {
		return this.description;
	}
	
    public static Builder getBuilder(String name, EduGroup eduGroup) {
    	return new Builder(name, eduGroup);	
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

    public EduGroup getEduGroup() {
        return eduGroup;
    }

    public void setEduGroup(EduGroup eduGroup) {
        this.eduGroup = eduGroup;
    }

    public Set<Campus> getGrades() {
        return compus;
    }

    public void setGrades(Set<Campus> compus) {
        this.compus = compus;
    }

	public void update(String name) {
		// TODO Auto-generated method stub
		this.name = name;	
	}
	
    public static class Builder {

        private School built;

        public Builder() {
            // TODO Auto-generated constructor stub
            built = new School();
        }

        public School build() {
            return built;
        }

        public Builder(String name,EduGroup eduGroup) {
            built = new School();
            built.name = name;
            built.eduGroup = eduGroup;
        }

        public Builder Grade(Set<Campus> compus) {
            built.compus = compus;
            return this;
        }

		public Builder description(String description) {
            built.description = description;
            return this;
        }
				
	}
    
    public SchoolDTO createDTO(School model) {
        SchoolDTO dto = new SchoolDTO();
        dto.setId(model.id);
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        return dto;
    }


}
