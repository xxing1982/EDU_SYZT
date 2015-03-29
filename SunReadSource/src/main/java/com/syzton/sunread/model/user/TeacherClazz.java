package com.syzton.sunread.model.user;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jerry on 3/29/15.
 */
@Entity
public class TeacherClazz extends AbstractEntity{
    private long teacherId;

    private long clazzId;

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getClazzId() {
        return clazzId;
    }

    public void setClazzId(long clazzId) {
        this.clazzId = clazzId;
    }
}
