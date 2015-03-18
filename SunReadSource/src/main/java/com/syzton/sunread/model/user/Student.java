package com.syzton.sunread.model.user;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="student")
@DiscriminatorValue("S")
public class Student extends User{

    public static final int MAX_LENGTH_IDENTITYID = 16;
    @Column(nullable = false,length = MAX_LENGTH_IDENTITYID)
    private String identityId;

    private int level ;

    private int coin;

    private int experience;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}

