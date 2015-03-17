package com.syzton.sunread.model.education_system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
@Entity
@Table(name="school")
@JsonIgnoreProperties
public class School extends EducationSys{


    public static final int MAX_LENGTH_DESCRIPTION = 500;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "school")
    @Basic(fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH },optional = false)
    @JoinColumn(name = "edu_group")
    private EduGroup eduGroup;


    public School() {
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

    public EduGroup getEduGroup() {
        return eduGroup;
    }

    public void setEduGroup(EduGroup eduGroup) {
        this.eduGroup = eduGroup;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }


    public static class Builder {

        private School built;

        public Builder() {
            // TODO Auto-generated constructor stub
            built = new School();
        }

        public School build() {
            return built;
        }

        public Builder(String name,EduGroup eduGroup) {
            built = new School();
            built.name = name;
            built.eduGroup = eduGroup;
        }

        public Builder Grade(Set<Grade> grades) {
            built.grades = grades;
            return this;
        }


    }
}
