package com.syzton.sunread.model.user;

import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.organization.Clazz;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="student")
@DiscriminatorValue("S")
public class Student extends User{

    public static final int MAX_LENGTH_IDENTITY = 16;

    @Column(length = MAX_LENGTH_IDENTITY)
    private String identity;

    private int level ;

    private int coin;

    private int point;

    @Transient
    private long enrollmentTime; // for receive json

    private DateTime enrollmentDate; // for insert DB

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private Clazz clazz;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<CoinHistory> coinHistorySet = new HashSet<>();

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public long getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(long enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public DateTime getEnrollmentDate() {
        return new DateTime(this.enrollmentTime);
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Set<CoinHistory> getCoinHistorySet() {
        return coinHistorySet;
    }

    public void setCoinHistorySet(Set<CoinHistory> coinHistorySet) {
        this.coinHistorySet = coinHistorySet;
    }
}

