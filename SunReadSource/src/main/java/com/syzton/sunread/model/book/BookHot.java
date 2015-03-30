package com.syzton.sunread.model.book;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jerry on 3/30/15.
 */
@Entity
public class BookHot extends AbstractEntity{

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

    public void setYearlyRecommend(long yearlyRecommend) {
        this.yearlyRecommend = yearlyRecommend;
    }
}
