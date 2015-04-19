package com.syzton.sunread.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.util.DateSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by jerry on 3/8/15.
 */
@Entity
@Table(name = "review")
public class Review {

    private final static int CONTENT_MAX_LENGTH = 1000;
    private final static int TITLE_MAX_LENGTH = 200;



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    @Column(length = TITLE_MAX_LENGTH,nullable = false)
    private String title;

    @Column(name ="content",nullable = false,length = CONTENT_MAX_LENGTH)
    private String content;
    
    private Long studentId;

    private String studentName;

    private int rate;

  
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @JoinColumn(name="book_id")
    @JsonIgnore
    private Book book;

    public Book getBook() {
        return book;
    }
    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
    }
    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public String getStudentName() {
        return studentName;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }
    public static Builder getBuilder(Long studentId,String title,String content,String studentName) {
        return new Builder(studentId,title,content,studentName);
    }

    public static class Builder {

        private Review built;

        public Builder(Long studentId,String title,String content,String studentName) {
            built = new Review();
            built.content = content;
            built.title = title;
            built.studentId=studentId;
            built.studentName = studentName;
        }
        public Builder book(Book book) {
            built.book = book;
            return this;
        }

        public Builder rate(int rate){
            built.rate = rate;
            return this;
        }

        public Review build() {
            return built;
        }


    }
    public Long getStudentId() {
  		return studentId;
  	}
  	public void setStudentId(Long studentId) {
  		this.studentId = studentId;
  	}
    public ReviewDTO createDTO(Review model) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setContent(model.getContent());
        dto.setStudentId(model.getStudentId());
        dto.setRate(model.getRate());
        dto.setStudentName(model.getStudentName());
        return dto;
    }

}
