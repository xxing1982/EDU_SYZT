package com.syzton.sunread.model.book;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    @Column(name ="content",nullable = false)
    private String content;


    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @JoinColumn(name="book_id")
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

    public long getId() {
        return id;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }
    public static Builder getBuilder(String content) {
        return new Builder(content);
    }

    public static class Builder {

        private Review built;

        public Builder(String content) {
            built = new Review();
            built.content = content;
        }
        public Builder book(Book book) {
            built.book = book;
            return this;
        }

        public Review build() {
            return built;
        }


    }
    public ReviewDTO createDTO(Review model) {
        ReviewDTO dto = new ReviewDTO();
        dto.setBookId(model.getBook().getId());
        dto.setContent(model.getContent());
        return dto;
    }

}
