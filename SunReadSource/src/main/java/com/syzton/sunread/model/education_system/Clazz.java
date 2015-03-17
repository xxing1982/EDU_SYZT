package com.syzton.sunread.model.education_system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Entity
@Table(name="clazz")
@JsonIgnoreProperties(value = {"categories" })
public class Clazz extends  EducationSys{

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


    public static class Builder {

        private Clazz built;

        public Builder() {
            // TODO Auto-generated constructor stub
            built = new Clazz();
        }

        public Clazz build() {
            return built;
        }

        public Builder(String name,Grade grade) {
            built = new Clazz();
            built.name = name;
            built.grade = grade;
        }

    }

}
