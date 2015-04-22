package com.syzton.sunread.model.fish;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by jerry on 4/21/15.
 */
@Entity
public class StudentFish extends AbstractEntity{

    private long studentId;

    @OneToOne
    @JoinColumn(name = "fish_id")
    private Fish fish;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public Fish getFish() {
        return fish;
    }

    public void setFish(Fish fish) {
        this.fish = fish;
    }
}
