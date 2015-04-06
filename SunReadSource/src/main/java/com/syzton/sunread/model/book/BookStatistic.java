package com.syzton.sunread.model.book;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jerry on 3/30/15.
 */
@Entity
public class BookStatistic extends AbstractEntity{
    //total test pass number
    private int hots;
    //total recommend number
    private int recommends;
    //total write note number
    private int notes;
    // total add bookShelf number
    private int readNums;

    private long weeklyRecommend;

    private long monthlyRecommend;

    private long yearlyRecommend;


    private long weeklyHot;

    private long monthlyHot;

    private long yearlyHot;

    public long getWeeklyHot() {
        return weeklyHot;
    }

    public void setWeeklyHot(long weeklyHot) {
        this.weeklyHot = weeklyHot;
    }

    public long getMonthlyHot() {
        return monthlyHot;
    }

    public void setMonthlyHot(long monthlyHot) {
        this.monthlyHot = monthlyHot;
    }

    public long getYearlyHot() {
        return yearlyHot;
    }

    public void setYearlyHot(long yearlyHot) {
        this.yearlyHot = yearlyHot;
    }

    public long getWeeklyRecommend() {
        return weeklyRecommend;
    }

    public void setWeeklyRecommend(long weeklyRecommend) {
        this.weeklyRecommend = weeklyRecommend;
    }

    public long getMonthlyRecommend() {
        return monthlyRecommend;
    }

    public void setMonthlyRecommend(long monthlyRecommend) {
        this.monthlyRecommend = monthlyRecommend;
    }

    public long getYearlyRecommend() {
        return yearlyRecommend;
    }

    public void setYearlyRecommend(long yearlyRecommend)
    {
        this.yearlyRecommend = yearlyRecommend;
    }

    public int getHots() {
        return hots;
    }

    public void increaseHots()
    {
        this.hots = this.hots++;
    }

    public int getRecommends() {
        return recommends;
    }

    public void increaseRecommends()
    {
        this.recommends = this.recommends++;
    }

    public int getNotes() {
        return notes;
    }

    public void increaseNotes()
    {
        this.notes = this.notes++;
    }

    public int getReadNums() {
        return readNums;
    }

    public void increaseReadNums() {
        this.readNums = this.readNums++;
    }

    public void setHots(int hots) {
        this.hots = hots;
    }

    public void setRecommends(int recommends) {
        this.recommends = recommends;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }

    public void setReadNums (int reads) {
        this.readNums = reads;
    }
}
