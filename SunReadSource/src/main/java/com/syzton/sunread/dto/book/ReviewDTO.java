package com.syzton.sunread.dto.book;

/**
 * Created by jerry on 3/9/15.
 */
public class ReviewDTO {

    private Long bookId;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
