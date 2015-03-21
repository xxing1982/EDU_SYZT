package com.syzton.sunread.model.book;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.*;

/**
 * Created by jerry on 3/21/15.
 */
@Entity
@Table(name = "dictionary")
public class Dictionary extends AbstractEntity{

    public static final int MAX_LENGTH_DESCRIPTION = 50;

    public static final int MAX_LENGTH_NAME = 20;
    @Enumerated(EnumType.STRING)
    private DictionaryType type;

    @Column(nullable = false,length = MAX_LENGTH_NAME)
    private String name ;

    private int value;

    @Column(length = MAX_LENGTH_DESCRIPTION)
    private String description;

    public DictionaryType getType() {
        return type;
    }

    public void setType(DictionaryType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
