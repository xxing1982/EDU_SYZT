package com.syzton.sunread.dto.book;

/**
 * Created by jerry on 3/9/15.
 */
public class ReviewDTO {

    private Long id;

    private Long bookId;

    private String content;
    
    private Long studentId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
    
   
}
