package com.syzton.sunread.model.store;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by jerry on 4/20/15.
 */
@Entity
public class Gift extends AbstractEntity{

    private String name ;

    private String description;

    private float price;

    private int coin;

    private boolean exchangeable;

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


}
