package com.syzton.sunread.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 3/20/15.
 */
@Entity
@Table(name = "book_extra")
public class BookExtra extends AbstractEntity{

    private int level;

    private int literature;

    private int language;

    private int grade;

    private int category;

    private int ageRange;

    private boolean hasVerifyTest;

    private boolean hasWordTest;

    private boolean hasThinkTest;

    private boolean hasVideo;

    private boolean hasRadio;

    private boolean hasEbook;

    @Transient
    private int pointRange;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "book")
//    private List<Recommendation> recommendations = new ArrayList<>();


    //    @JsonIgnore
//    @ManyToMany
//    @JoinTable(name="book_category",
//            joinColumns = @JoinColumn(name="book_id", referencedColumnName="id"),
//            inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName="id")
//    )
//    private Set<Category> categories = new HashSet<>();


    public boolean isHasVerifyTest() {
        return hasVerifyTest;
    }

    public void setHasVerifyTest(boolean hasVerifyTest) {
        this.hasVerifyTest = hasVerifyTest;
    }

    public boolean isHasThinkTest() {
        return hasThinkTest;
    }

    public void setHasThinkTest(boolean hasThinkTest) {
        this.hasThinkTest = hasThinkTest;
    }

    public boolean isHasWordTest() {
        return hasWordTest;
    }

    public void setHasWordTest(boolean hasWordTest) {
        this.hasWordTest = hasWordTest;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public boolean isHasRadio() {
        return hasRadio;
    }

    public void setHasRadio(boolean hasRadio) {
        this.hasRadio = hasRadio;
    }

    public boolean isHasEbook() {
        return hasEbook;
    }

    public void setHasEbook(boolean hasEbook) {
        this.hasEbook = hasEbook;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getPointRange() {
        return pointRange;
    }

    public void setPointRange(int pointRange) {
        this.pointRange = pointRange;
    }
}
