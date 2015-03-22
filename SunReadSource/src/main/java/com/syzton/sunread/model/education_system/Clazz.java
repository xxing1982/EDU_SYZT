package com.syzton.sunread.model.education_system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syzton.sunread.dto.education_system.ClazzDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Entity
@Table(name="clazz")
@JsonIgnoreProperties(value = {"grade"})
public class Clazz extends  AbstractEntity{


    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    
    @Column(name ="name", nullable = false, length = MAX_LENGTH_NAME)
    private String name; 

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },optional = false)
    @JoinColumn(name = "grade")
    private Grade grade;

    public Clazz() {
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }

	public String getName() {
		return name;
	}
	
    public static Builder getBuilder(String name, Grade grade) {
    	return new Builder(name, grade);	
	}
    
    public String getDescription() {
		return description;
	}


    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
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

	public void update(String name) {
		this.name = name;
	}

    public static class Builder {

        private Clazz built;

        public Builder() {
            built = new Clazz();
        }

        public Clazz build() {
            return built;
        }
        
        public Builder(String name) {
            built = new Clazz();
            built.name = name;
        }

        public Builder(String name,Grade grade) {
            built = new Clazz();
            built.name = name;
            built.grade = grade;
        }

		public Builder description(String description) {
			// TODO Auto-generated method stub
			built.description = description;
			return this;
		}        
    }
    
    public ClazzDTO createDTO(Clazz model) {
        ClazzDTO dto = new ClazzDTO();
        dto.setId(model.id);
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        return dto;
    }
    

}
