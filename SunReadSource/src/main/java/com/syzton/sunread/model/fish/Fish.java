package com.syzton.sunread.model.fish;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.Entity;

/**
 * Created by jerry on 4/21/15.
 */
@Entity
public class Fish extends AbstractEntity {

    private static final String DEFAULT_FISH_PIC_URL = "default_fish" ;

    private String name;

    private String fishPic = DEFAULT_FISH_PIC_URL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFishPic() {
        return fishPic;
    }

    public void setFishPic(String fishPic) {
        this.fishPic = fishPic;
    }
}
