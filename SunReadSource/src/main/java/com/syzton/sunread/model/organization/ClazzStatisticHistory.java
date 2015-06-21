package com.syzton.sunread.model.organization;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.PrePersist;

/**
 * Created by jerry on 4/15/15.
 */
@Entity
public class ClazzStatisticHistory extends AbstractEntity{
    private long clazzId;
    private long semesterId;

    private int avgPoints;

    private int avgReads;

    private int totalPoints;

    private int totalReads;

    private long campusId;

    public long getCampusId() {
        return campusId;
    }

    public void setCampusId(long campusId) {
        this.campusId = campusId;
    }

    public long getClazzId() {
        return clazzId;
    }

    public void setClazzId(long clazzId) {
        this.clazzId = clazzId;
    }

    public long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(long semesterId) {
        this.semesterId = semesterId;
    }

    public int getAvgPoints() {
        return avgPoints;
    }

    public void setAvgPoints(int avgPoints) {
        this.avgPoints = avgPoints;
    }

    public int getAvgReads() {
        return avgReads;
    }

    public void setAvgReads(int avgReads) {
        this.avgReads = avgReads;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getTotalReads() {
        return totalReads;
    }

    public void setTotalReads(int totalReads) {
        this.totalReads = totalReads;
    }
}
