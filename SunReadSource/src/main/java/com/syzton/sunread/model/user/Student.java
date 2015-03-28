package com.syzton.sunread.model.user;

import com.syzton.sunread.model.coinhistory.CoinHistory;

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

    private long classId;

    private long gradeId;

    private long schoolId;

    private long enrollmentTime;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<CoinHistory> coinHistorySet = new HashSet<>();

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

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public long getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(long enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public Set<CoinHistory> getCoinHistorySet() {
        return coinHistorySet;
    }

    public void setCoinHistorySet(Set<CoinHistory> coinHistorySet) {
        this.coinHistorySet = coinHistorySet;
    }
}

