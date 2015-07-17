package com.syzton.sunread.model.organization;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.dto.organization.CategoryCountDTO;
import com.syzton.sunread.dto.organization.ClazzDTO;
import com.syzton.sunread.model.common.AbstractEntity;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Entity
@Table(name="clazz")
//@JsonIgnoreProperties(value = {"grade"})
public class Clazz extends  AbstractEntity{


    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    
    @Column(name ="name", nullable = false, length = MAX_LENGTH_NAME)
    private String name; 
    
    @Column(name = "grade", nullable = false)
    private int grade = 0;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime modificationTime;
  
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "campus")
    @JsonIgnore
    private Campus campus;
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "calzz_statistic_id")
    private ClazzStatistic clazzStatistic = new ClazzStatistic();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "clazz_id")
    private Set<ClazzCategoryCount> categoryCount = new HashSet<>();


    public ClazzStatistic getClazzStatistic() {
        return clazzStatistic;
    }

    public void setClazzStatistic(ClazzStatistic clazzStatistic) {
        this.clazzStatistic = clazzStatistic;
    }

    public Clazz() {
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }

	public void setName(String name) {
		this.name = name;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setModificationTime(DateTime modificationTime) {
		this.modificationTime = modificationTime;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public void setCategoryCount(Set<ClazzCategoryCount> categoryCount) {
		this.categoryCount = categoryCount;
	}

	public String getName() {
		return name;
	}
	
    public static Builder getBuilder(String name, int grade, Campus campus) {
    	return new Builder(name, grade, campus);	
	}
    
    public String getDescription() {
		return description;
	}


    public int getGrade() {
        return grade;
    }

    public Campus getCampus() {
        return campus;
    }

    public Set<ClazzCategoryCount> getCategoryCount() {
        return categoryCount;
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

        public Builder(String name,int grade, Campus campus) {
            built = new Clazz();
            built.name = name;
            built.grade = grade;
            built.campus = campus;
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
        dto.setGrade(model.grade);
        //dto.setCampusId(model.campus.getId());
        dto.setCampusName(model.campus.getName());
        dto.setDescription(model.getDescription());

        for (ClazzCategoryCount c : model.getCategoryCount()){
            CategoryCountDTO cDto = new CategoryCountDTO();
            cDto.setCount(c.getCount());
            cDto.setCategoryName(c.getDictionary().getName());
            dto.getCategoryCountDTOs().add(cDto);
        }

        return dto;
    }
    

}
