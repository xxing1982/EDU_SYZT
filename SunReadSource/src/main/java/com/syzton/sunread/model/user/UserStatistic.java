package com.syzton.sunread.model.user;

import java.io.Serializable;

import javax.persistence.Entity;

import com.syzton.sunread.model.common.AbstractEntity;

/**
 * Created by jerry on 3/30/15.
 */
@Entity
public class UserStatistic extends AbstractEntity implements Serializable{
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
    // total tests
    private int testCount;

    private int level ;

    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }

    public void increaseLevel() {
        this.level = ++this.level;
    }

    public int getPoint() {
        return point;
    }

    public int getCoin() {
        return coin;
    }

    public int getNotes() {
        return notes;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void increaseNotes()
    {
        this.notes = ++this.notes;
    }

    public int getReadNum() {
        return readNum;
    }

    public void increaseReadNum() {
        this.readNum = ++this.readNum;
    }

    public int getTestPasses() {
        return testPasses;
    }

    public void increaseTestPasses()
    {
        this.testPasses = ++this.testPasses;
    }

    public int getTestCount() {
        return testCount;
    }

    public void increaseTestCounts() {
        this.testCount = ++this.testCount;
    }
}
