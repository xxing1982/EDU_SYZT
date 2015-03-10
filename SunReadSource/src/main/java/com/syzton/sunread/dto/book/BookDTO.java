package com.syzton.sunread.dto.book;

import com.syzton.sunread.model.book.Review;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jerry on 3/8/15.
 */
public class BookDTO {

    private Long id;

    private String isbn;

    private String name;

    private String description;

    private Date publicationDate;

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    private Set<ReviewDTO> reviewSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ReviewDTO> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(Set<ReviewDTO> reviewSet) {
        this.reviewSet = reviewSet;
    }
}
