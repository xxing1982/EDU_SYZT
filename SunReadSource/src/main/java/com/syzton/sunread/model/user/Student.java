package com.syzton.sunread.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.util.DateSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="student")
@DiscriminatorValue("S")
public class Student extends User{

    public static final int MAX_LENGTH_IDENTITY = 16;

    @Column(length = MAX_LENGTH_IDENTITY)
    private String identity;

    private int level ;

    @NotNull
    private long schoolId;
    @NotNull
    private long clazzId;
    @NotNull
    private long gradeId;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = true)
    private Task task = new Task();
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = true)
    private UserStatistic statistic = new UserStatistic();


    @Transient
    private long enrollmentTime; // for receive json

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime enrollmentDate; // for insert DB

    @Column(nullable = true)
    private Long classId;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<CoinHistory> coinHistorySet = new HashSet<>();


    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.enrollmentDate = new DateTime(this.enrollmentTime);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public long getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(long enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }


    public Set<CoinHistory> getCoinHistorySet() {
        return coinHistorySet;
    }

    public void setCoinHistorySet(Set<CoinHistory> coinHistorySet) {
        this.coinHistorySet = coinHistorySet;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task)   {
        this.task = task;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public long getClazzId() {
        return clazzId;
    }

    public void setClazzId(long clazzId) {
        this.clazzId = clazzId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId)
    {
        this.gradeId = gradeId;
    }

    public UserStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(UserStatistic statistic) {
        this.statistic = statistic;
    }
}

