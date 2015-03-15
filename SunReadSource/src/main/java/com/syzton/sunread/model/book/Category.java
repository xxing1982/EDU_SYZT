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

    public static final int MAX_LENGTH_NAME = 100;

    @Column(name="name",nullable = false,length = MAX_LENGTH_NAME)
    private String name;


    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @OrderColumn
    private Set<Category> children = new HashSet<>() ;


//    @ManyToMany(cascade = CascadeType.REFRESH)
//    @Basic(fetch = FetchType.LAZY)
//    @JoinTable(name="book_category",
//            joinColumns=
//            @JoinColumn(name="category_id", referencedColumnName="id"),
//            inverseJoinColumns=
//            @JoinColumn(name="book_id", referencedColumnName="id")
//    )
//    private Set<Book> books = new HashSet<>();


    public Category() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Book> getBooks() {
//        return books;
//    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
