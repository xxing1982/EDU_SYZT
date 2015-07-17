package com.syzton.sunread.model.store;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.syzton.sunread.model.common.AbstractEntity;

/**
 * Created by jerry on 4/20/15.
 */
@Entity
public class Gift extends AbstractEntity{

    private String name ;

    private String description;

    private float price;

    private String picture;

    private int coin;

    private boolean exchangeable;

    private long campusId;

    private long userId;

    private String userName;


    @Enumerated(EnumType.STRING)
    private GiftType giftType;



    public String getName() {
        return name;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isExchangeable() {
        return exchangeable;
    }

    public void setExchangeable(boolean exchangeable) {
        this.exchangeable = exchangeable;
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public void setGiftType(GiftType giftType) {
        this.giftType = giftType;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getCampusId() {
        return campusId;
    }

    public void setCampusId(long campusId) {
        this.campusId = campusId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
