package com.syzton.sunread.model.book;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syzton.sunread.model.common.AbstractEntity;

/**
 * @author Jerry zhang
 */
@Entity
@Table(name="category")
public class Category extends AbstractEntity{

    public static final int MAX_LENGTH_NAME = 20;

    private int value;


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



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
