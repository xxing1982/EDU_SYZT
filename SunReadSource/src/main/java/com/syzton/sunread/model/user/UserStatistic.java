package com.syzton.sunread.model.user;

import java.io.Serializable;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

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

    private int level ;

    public int getLevel() {
        return level;
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

    public void increaseTestPasses() {
        this.testPasses = ++this.testPasses;
    }
}
