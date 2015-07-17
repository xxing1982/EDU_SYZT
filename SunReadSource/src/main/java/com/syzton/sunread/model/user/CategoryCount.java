package com.syzton.sunread.model.user;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.common.AbstractEntity;

/**
 * Created by jerry on 4/28/15.
 */
@Entity
public class CategoryCount extends AbstractEntity{

    @OneToOne
    private Dictionary dictionary;

    private int count;

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        ++this.count ;
    }
}
