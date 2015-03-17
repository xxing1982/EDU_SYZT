package com.syzton.sunread.model.education_system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Entity
@Table(name="grade")
@JsonIgnoreProperties
public class Grade extends EducationSys{


    public static final int MAX_LENGTH_DESCRIPTION = 500;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @ManyToOne(cascade ={CascadeType.MERGE,CascadeType.REFRESH},optional = false)
    @JoinColumn(name = "school")
    private School school;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade")
    @Basic(fetch = FetchType.LAZY)
    private Set<Clazz> clazzs = new HashSet<>();


    public Grade() {
    }

    public DateTime getModificationTime() {
        return modificationTime;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Clazz> getClazzs() {
        return clazzs;
    }

    public void setClazzs(Set<Clazz> clazzs) {
        this.clazzs = clazzs;
    }


    public static class Builder {

        private Grade built;

        public Builder() {
            // TODO Auto-generated constructor stub
            built = new Grade();
        }

        public Grade build() {
            return built;
        }

        public Builder(String name,School school) {
            built = new Grade();
            built.name = name;
            built.school = school;
        }

        public Builder clazzs(Set<Clazz> clazzs){
            built.clazzs = clazzs;
            return this;
        }

    }
}
