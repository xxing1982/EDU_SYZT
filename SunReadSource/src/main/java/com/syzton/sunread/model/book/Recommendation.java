package com.syzton.sunread.model.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.syzton.sunread.model.common.AbstractEntity;

/**
 * Created by jerry on 3/8/15.
 */
@Entity
@Table(name = "recommendation")
public class Recommendation extends AbstractEntity{

    @Column(nullable = false)
    private Long bookId;
    @Column(nullable = false)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

}

