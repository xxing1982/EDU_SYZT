package com.syzton.sunread.model.book;

import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.*;

/**
 * Created by jerry on 3/8/15.
 */
@Entity
@Table(name = "quality")
public class Quality  extends AbstractEntity{


    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="book_id")
    private Book book;

    private int recommendation;

    private int passCount;

    private int testCount;



    public void increaseTestCount(){
        testCount++;
    }
    public void increaseRecommendation(){
        recommendation++;
    }
    public void increasePassCount(){
        passCount++;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public int getRecommendation() {
        return recommendation;
    }

    public int getPassCount() {
        return passCount;
    }

    public int getTestCount() {
        return testCount;
    }
}
