package com.syzton.sunread.model.user;

import java.io.Serializable;

import javax.persistence.Entity;

import com.syzton.sunread.model.common.AbstractEntity;

/**
 * Created by jerry on 3/30/15.
 */
@Entity
public class UserStatisticHistory extends AbstractEntity implements Serializable{

    private long studentId;

    private long semesterId;

    private long campusId;
    //current point
    private int point;
    //current coin
    private int coin;
    //total write note number
    private int notes;
    // total add bookShelf number
    private int readNum;
    //total testPassed
    private int testPasses;

    private int testCounts;

    private int level ;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(long semesterId) {
        this.semesterId = semesterId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getNotes() {
        return notes;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getTestPasses() {
        return testPasses;
    }

    public void setTestPasses(int testPasses) {
        this.testPasses = testPasses;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public long getCampusId() {
        return campusId;
    }

    public void setCampusId(long campusId) {
        this.campusId = campusId;
    }

    public int getTestCounts() {
        return testCounts;
    }

    public void setTestCounts(int testCounts) {
        this.testCounts = testCounts;
    }
}
