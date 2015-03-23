package com.syzton.sunread.model.user;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="parent")
@DiscriminatorValue("P")
public class Parent extends User{

    private String workUnit;


    @ManyToMany
    @JoinTable(name="parent_student",
            joinColumns = @JoinColumn(name="parent_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="student_id", referencedColumnName="id")
    )
    private Set<Student> studentSet = new HashSet<>();


    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }
}

