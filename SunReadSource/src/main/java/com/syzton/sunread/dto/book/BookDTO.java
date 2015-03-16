package com.syzton.sunread.dto.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syzton.sunread.model.book.Review;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jerry on 3/8/15.
 */
public class BookDTO {

    private Long id;

    @NotNull
    private String isbn;

    @NotNull
    private String name;

    private String description;

    private Date publicationDate;

    @JsonIgnore
    private Set<Long> categories = new HashSet<>();

    public Set<Long> getCategories() {
        return categories;
    }

    public void setCategories(Set<Long> categories) {
        this.categories = categories;
    }

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
