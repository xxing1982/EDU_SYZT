package com.syzton.sunread.model.organization;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.organization.CampusDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.region.Region;

/**
 * @author Morgan-Leon
 * @Date 2015年4月6日
 * 
 */
@Entity
@Table(name="campus")
public class Campus extends AbstractEntity{
    
	public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    public static final int MAX_LENGTH_HEADMASTER = 500;
    
    @Column(name ="name", nullable = false, unique = true,length = MAX_LENGTH_NAME)
    private String name;
    
    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;
    
    @Column(name = "headmaster", nullable = false, length = MAX_LENGTH_HEADMASTER)
    private String headmaster;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "campus")
    @Basic(fetch = FetchType.LAZY)
    private Set<Clazz> clazz = new HashSet<Clazz>();
    
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "region")
    private Region region;
    
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "school")
    private School school;

	private String wish;
    
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public DateTime getModificationTime() {
		return modificationTime;
	}

	public String getHeadmaster() {
		return headmaster;
	}

	public Set<Clazz> getClazz() {
		return clazz;
	}
	
	public int clazzNum() {
		return this.clazz.size();
	}

	public Region getRegion() {
		return region;
	}

	public School getSchool() {
		return school;
	}

	public String getWish() {
		return wish;
	}

	public void setWish(String wish) {
		this.wish = wish;
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
    
    public static class Builder{
    	private Campus built;
    	
    	public Builder(String name, String headmaster
    			,Region region, School school){
    		built = new Campus();
    		built.name = name;
    		built.headmaster = headmaster;
    		built.region = region;
    		built.school = school;   		
    	}
    	
    	public Campus build() {
			return built;					
		}
    	
		public Builder description(String description) {
			// TODO Auto-generated method stub
			built.description = description;
			return this;
		}
		public Builder wish(String wish) {
			built.wish = wish;
			return this;
		}
	}

	/**
	 */
	public void update(String name,String headmaster) {
		this.name = name;
		this.headmaster = headmaster;
	}
    
	/**
	 * @return
	 */
	public static Builder getBuilder(String name, String headmaster,
			Region region, School school) {
		// TODO Auto-generated method stub
		return new Builder(name, headmaster, region, school);
	}

    public CampusDTO createDTO(Campus model) {
        CampusDTO dto = new CampusDTO();
        dto.setId(model.id);
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setHeadmaster(model.headmaster);
        dto.setRegionId(model.region.getId());
        dto.setAddress(model.region.generateAddress());
        dto.setSchool(model.school.getName());
        dto.setClassNum(model.clazzNum());
		dto.setWish(model.getWish());
        
        return dto;
    }
    
    
}
