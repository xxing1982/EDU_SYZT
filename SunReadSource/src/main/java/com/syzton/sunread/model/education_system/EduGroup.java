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
@Table(name="edu_group")
@JsonIgnoreProperties
public class EduGroup extends EducationSys{


    public static final int MAX_LENGTH_DESCRIPTION = 500;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @OneToMany(cascade = CascadeType.ALL)
    @Basic(fetch = FetchType.LAZY)
    private Set<School> schools = new HashSet<>();


    public EduGroup() {
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

    public Set<School> getSchools() {
        return schools;
    }

    public void setSchools(Set<School> schools) {
        this.schools = schools;
    }


    public static class Builder {

        private EduGroup built;

        public Builder() {
            // TODO Auto-generated constructor stub
            built = new EduGroup();
        }

        public EduGroup build() {
            return built;
        }

        public Builder(String name) {
            built = new EduGroup();
            built.name = name;
        }

        public  Builder schools(Set<School> schools)
        {
            built.schools = schools;
            return this;
        }

    }
}
