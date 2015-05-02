package com.syzton.sunread.model.user;

import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.Grade;
import com.syzton.sunread.repository.organization.ClazzRepository;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    private long classId;

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

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }
}

