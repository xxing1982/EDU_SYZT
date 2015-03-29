package com.syzton.sunread.model.task;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jerry on 3/28/15.
 */
@Entity
public class Task extends AbstractEntity{

    private long teachId;

    private long studentId;

    private int bookNum;

    private int point;

    public long getTeachId() {
        return teachId;
    }

    public void setTeachId(long teachId) {
        this.teachId = teachId;
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
