package com.syzton.sunread.model.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="teacher")
@DiscriminatorValue("T")
//@Configurable
public class Teacher extends User{

//    @Transient
//    private ClazzRepository clazzRepository;
//
//    @Autowired
//    public Teacher(ClazzRepository clazzRepository) {
//        this.clazzRepository = clazzRepository;
//    }

    private int rank;

    private int experience;

    private String graduateSchool;

    @Transient
    private long currentClassId;

    private long campusId;

    private String teaching;

    public String getTeaching() {
        return teaching;
    }

    public void setTeaching(String teaching) {
        this.teaching = teaching;
    }

    //    @ManyToMany
//    @JoinTable(name="teacher_clazz",
//            joinColumns = @JoinColumn(name="teacher_id", referencedColumnName="id"),
//            inverseJoinColumns = @JoinColumn(name="clazz_id", referencedColumnName="id")
//    )
//    private Set<Clazz> clazzs = new HashSet<>();


//    @PrePersist
//    public void prePersist(){
//        super.prePersist();
//        for(Long clazzId: clazzIds){
//            Clazz clazz = clazzRepository.findOne(clazzId);
//            if(clazz!=null){
//                clazzs.add(clazz);
//            }
//        }
//
//    }


    public long getCampusId() {
        return campusId;
    }

    public void setCampusId(long campusId) {
        this.campusId = campusId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

//
//    public Set<Clazz> getClazzs() {
//        return clazzs;
//    }
//
//    public void setClazzs(Set<Clazz> clazzs) {
//        this.clazzs = clazzs;
//    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public long getCurrentClassId() {
        return currentClassId;
    }

    public void setCurrentClassId(long currentClassId) {
        this.currentClassId = currentClassId;
    }
}

