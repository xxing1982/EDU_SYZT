package com.syzton.sunread.model.task;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jerry on 3/28/15.
 */
@Entity
public class Task extends AbstractEntity{

    private long teacherId;

    private long studentId;

    private int bookNum;

    private int point;

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getBookNum() {
        return bookNum;
    }

    public void setBookNum(int bookNum) {
        this.bookNum = bookNum;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
