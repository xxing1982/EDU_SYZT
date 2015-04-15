package com.syzton.sunread.model.organization;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.PrePersist;

/**
 * Created by jerry on 4/15/15.
 */
@Entity
public class ClazzStatistic extends AbstractEntity{

    private int studentNum;

    private int avgPoints;

    private int avgReads;

    private int totalPoints;

    private int totalReads;

    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.avgPoints = this.getAvgPoints();
        this.avgReads = this.getAvgReads();

    }

    public void increaseStudentNum(){
        ++this.studentNum;
    }

    public void setTotalPoints(int totalPoints){
        this.totalPoints = totalPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void increaseTotalReads() {
        ++this.totalReads;
    }

    public int getTotalReads() {
        return totalReads;
    }

    public int getAvgPoints() {
        if(studentNum == 0)
            return 0;
        return this.totalPoints/this.studentNum;
    }

    public int getAvgReads() {
        if(studentNum == 0)
            return 0;
        return this.totalReads/this.studentNum;
    }
}
