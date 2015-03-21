package com.syzton.sunread.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jerry on 3/20/15.
 */
@Entity
@Table(name = "book_extra")
public class BookExtra extends AbstractEntity{

    private int level;

    private int testType;

    private int literature;

    private int language;

    private int resource;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "book")
    private List<Recommendation> recomendations = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="book_category",
            joinColumns = @JoinColumn(name="book_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName="id")
    )
    private Set<Category> categories = new HashSet<>();



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }

    public int getLiterature() {
        return literature;
    }

    public void setLiterature(int literature) {
        this.literature = literature;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public List<Recommendation> getRecomendations() {
        return recomendations;
    }

    public void setRecomendations(List<Recommendation> recomendations) {
        this.recomendations = recomendations;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
