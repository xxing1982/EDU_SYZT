package com.syzton.sunread.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.util.DateSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
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

    private int coin;

    private int point;

    private int bookNum;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = true)
    private Task task = new Task();


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

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
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

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}

