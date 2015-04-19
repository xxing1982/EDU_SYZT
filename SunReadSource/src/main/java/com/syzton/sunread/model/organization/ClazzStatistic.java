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
        this.avgPoints = this.setAvgPoints();
        this.avgReads = this.setAvgReads();

    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
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
        return avgPoints;
    }

    public int getAvgReads() {
        return avgReads;
    }

    public int setAvgPoints() {
        if(studentNum == 0)
            return 0;
        this.avgPoints =  this.totalPoints/this.studentNum;
        return this.avgPoints;
    }

    public int setAvgReads() {
        if(studentNum == 0)
            return 0;
        this.avgReads = this.totalReads/this.studentNum;
        return this.avgReads;
    }
}
