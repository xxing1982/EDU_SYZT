package com.syzton.sunread.model.book;

import com.syzton.sunread.model.common.AbstractEntity;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry zhang
 */
@Entity
@Table(name="category")
public class Category extends AbstractEntity{

    public static final int MAX_LENGTH_NAME = 20;

    private int value;

    private CategoryType type;

    @Column(name="name",nullable = false,length = MAX_LENGTH_NAME)
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @OrderColumn
    private Set<Category> children = new HashSet<>() ;


    public Category() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
