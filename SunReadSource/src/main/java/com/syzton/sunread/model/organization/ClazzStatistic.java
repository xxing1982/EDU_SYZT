package com.syzton.sunread.model.organization;

import javax.persistence.Entity;
import javax.persistence.PrePersist;

import com.syzton.sunread.model.common.AbstractEntity;

/**
 * Created by jerry on 4/15/15.
 */
@Entity
public class ClazzStatistic extends AbstractEntity{

    private int studentNum;

    private int avgPoints;

    private int avgCoin;

    private int avgReads;

    private int avgReadWords;

    private int totalPoints;

    private int totalReads;

    private int totalReadWords;

    private int totalCoin;
    
    private int totalNote;



    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.avgPoints = this.setAvgPoints();
        this.avgReads = this.setAvgReads();
        this.avgReadWords = this.setAvgReadWords();
        this.avgCoin = this.setAvgCoin();
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

    public void setTotalCoin(int totalCoin) {
        this.totalCoin = totalCoin;
    }

    public int getTotalCoin() {
        return totalCoin;
    }
    
    public void increaseTotalNote() {
        ++this.totalNote;
    }
    
    public void setTotalNote(int totalNote) {
        this.totalNote = totalNote;
    }

    public int getTotalNote() {
        return totalNote;
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

    public int getAvgReadWords() {
        return avgReadWords;
    }

    public int getTotalReadWords() {
        return totalReadWords;
    }

    public void setTotalReadWords(int totalReadWords) {
        this.totalReadWords = totalReadWords;
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

    public int setAvgCoin() {
        if(studentNum == 0)
            return 0;
        this.avgCoin =  this.totalCoin/this.studentNum;
        return this.avgCoin;
    }

    public int setAvgReadWords() {
        if(studentNum == 0)
            return 0;
        this.avgPoints =  this.totalReadWords/this.studentNum;
        return this.avgPoints;
    }

    public int setAvgReads() {
        if(studentNum == 0)
            return 0;
        this.avgReads = this.totalReads/this.studentNum;
        return this.avgReads;
    }
}
