package com.syzton.sunread.model.task;

import java.io.Serializable;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by jerry on 3/28/15.
 */
@Entity
public class Task extends AbstractEntity implements Serializable{


    private long teacherId;

    private int targetBookNum;

    private int targetPoint;

    public int getTargetBookNum() {
        return targetBookNum;
    }

    public void setTargetBookNum(int targetBookNum) {
        this.targetBookNum = targetBookNum;
    }

    public int getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(int targetPoint) {
        this.targetPoint = targetPoint;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

}
