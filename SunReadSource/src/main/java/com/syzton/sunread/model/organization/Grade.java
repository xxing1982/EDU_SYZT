//package com.syzton.sunread.model.organization;
//
//import com.syzton.sunread.dto.organization.GradeDTO;
//import com.syzton.sunread.model.common.AbstractEntity;
//
//import org.hibernate.annotations.Type;
//import org.joda.time.DateTime;
//
//import javax.persistence.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created by Morgan-Leon on 2015/3/16.
// * @param <School>
// */
//public class Grade<School> extends AbstractEntity{
//
//
//    public static final int MAX_LENGTH_DESCRIPTION = 500;
//
//	public static final int MAX_LENGTH_NAME = 100;
//	
//    @Column(name ="name", nullable = false, length = MAX_LENGTH_NAME)
//    private String name; 
//
//    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
//    private String description;
//
//    @Column(name = "modification_time", nullable = false)
//    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
//    private DateTime modificationTime;
//
//    @ManyToOne(cascade ={CascadeType.MERGE,CascadeType.REFRESH},optional = false)
//    @JoinColumn(name = "school")
//    private School school;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade")
//    @Basic(fetch = FetchType.LAZY)
//    private Set<Clazz> clazzs = new HashSet<>();
//
//
//    public Grade() {
//    }
//
//    public DateTime getModificationTime() {
//        return modificationTime;
//    }
//
//	public String getName() {
//		return name;
//	}
//	
//    public static Builder getBuilder(String name, School school) {
//    	return new Builder(name, school);
//		
//	}
//    
//    public String getDescription() {
//		return description;
//	}
//
//
//    @PrePersist
//    public void prePersist() {
//        DateTime now = DateTime.now();
//        creationTime = now;
//        modificationTime = now;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        modificationTime = DateTime.now();
//    }
//
//    public School getSchool() {
//        return school;
//    }
//
//    public void setSchool(School school) {
//        this.school = school;
//    }
//
//    public Set<Clazz> getClazzs() {
//        return clazzs;
//    }
//
//    public void setClazzs(Set<Clazz> clazzs) {
//        this.clazzs = clazzs;
//    }
//
//	public void update(String name) {
//		// TODO Auto-generated method stub
//		this.name = name;			
//	}
//
//    public static class Builder {
//
//        private Grade built;
//
//        public Builder() {
//            // TODO Auto-generated constructor stub
//            built = new Grade();
//        }
//
//        public Grade build() {
//            return built;
//        }
//
//        public Builder(String name) {
//            built = new Grade();
//            built.name = name;
//        }
//        
//        public Builder(String name,School school) {
//            built = new Grade();
//            built.name = name;
//            built.school = school;
//        }
//
//        public Builder clazzs(Set<Clazz> clazzs){
//            built.clazzs = clazzs;
//            return this;
//        }
//        
//		public Builder description(String description) {
//            built.description = description;
//            return this;
//        }
//
//    }
//    
//    public GradeDTO createDTO(Grade model) {
//        GradeDTO dto = new GradeDTO();
//        dto.setId(model.id);
//        dto.setName(model.getName());
//        dto.setDescription(model.getDescription());
//        return dto;
//}
//}
