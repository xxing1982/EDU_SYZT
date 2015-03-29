package com.syzton.sunread.model.user;

import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.Grade;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="teacher")
@DiscriminatorValue("T")
public class Teacher extends User{

    private long schoolId;

    private int rank;

    private int experience;

    private String graduateSchool;


    @ManyToMany
    @JoinTable(name="teacher_clazz",
            joinColumns = @JoinColumn(name="teacher_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="clazz_id", referencedColumnName="id")
    )
    private Set<Clazz> clazzs;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public Set<Clazz> getClazzs() {
        return clazzs;
    }

    public void setClazzs(Set<Clazz> clazzs) {
        this.clazzs = clazzs;
    }

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
}

