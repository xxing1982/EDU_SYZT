package com.syzton.sunread.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.*;

/**
 * Created by jerry on 3/8/15.
 */
@Entity
@Table(name = "recommendation")
public class Recommendation extends AbstractEntity{

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="book_id")
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
