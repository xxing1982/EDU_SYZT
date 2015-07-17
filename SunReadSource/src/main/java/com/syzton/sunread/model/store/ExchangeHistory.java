package com.syzton.sunread.model.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;

/**
 * Created by jerry on 4/20/15.
 */
@Entity
public class ExchangeHistory extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "gift_id")
    private Gift gift;

    @Enumerated(EnumType.STRING)
    private GiftStatus status;

    private int count;

    private long studentId;

    private long modifier;

    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime modificationTime;

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
        modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        modificationTime = DateTime.now();
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public GiftStatus getStatus() {
        return status;
    }

    public void setStatus(GiftStatus status) {
        this.status = status;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getModifier() {
        return modifier;
    }

    public void setModifier(long modifier) {
        this.modifier = modifier;
    }
    public DateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(DateTime modificationTime) {
        this.modificationTime = modificationTime;
    }
}
