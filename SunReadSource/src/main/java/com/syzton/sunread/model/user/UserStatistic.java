package com.syzton.sunread.model.user;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jerry on 3/30/15.
 */
@Entity
public class UserStatistic extends AbstractEntity{
    //current point
    private int point;
    //current coin
    private int coin;
    //total write note number
    private int notes;
    // total add bookShelf number
    private int reads;
    //total testPassed
    private int testPasses;

    public int getPoint() {
        return point;
    }

    public void increaseHots()
    {
        this.point = this.point++;
    }

    public int getCoin() {
        return coin;
    }

    public void increaseRecommends()
    {
        this.coin = this.coin++;
    }

    public int getNotes() {
        return notes;
    }

    public void increaseNotes()
    {
        this.notes = this.notes++;
    }

    public int getReads() {
        return reads;
    }

    public void increaseReads()
    {
        this.reads = this.reads++;
    }

    public int getTestPasses() {
        return testPasses;
    }

    public void increaseTestPasses() {
        this.testPasses = this.testPasses++;
    }
}
