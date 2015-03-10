package com.syzton.sunread.model.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.ReviewDTO;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry zhang
 */
//@Entity
//@Table(name="book_category")
public class BookCategory {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    public static final int MAX_LENGTH_ISBN = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @Column(name="name",nullable = false,length = MAX_LENGTH_NAME)
    private String name;

    @ManyToMany(cascade = CascadeType.REFRESH,mappedBy = "book")
    @Basic(fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>() ;

    public BookCategory() {

    }

    public static Builder getBuilder(String isbn,String name) {
        return new Builder(isbn,name);
    }

    public Long getId() {
        return id;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Review> getReviews() {
        return reviews;
    }



    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
        modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        modificationTime = DateTime.now();
    }


    public static class Builder {

        private BookCategory built;

        public Builder(String isbn,String name) {
            built = new BookCategory();
            built.name = name;
        }

        public BookCategory build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }

        public Builder reviews(Set<Review> reviews){
            built.reviews = reviews;
            return this;
        }


    }
    public BookDTO createDTO(BookCategory model) {
        BookDTO dto = new BookDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        //  dto.setPublicationDate(new Date());
        // dto.setReviewSet(model.getReviews());
        return dto;
    }
    public BookDTO bookWithReview(BookCategory model){
       BookDTO dto = this.createDTO(model);
        Set<ReviewDTO> reviewDTOs = new HashSet<>();
        for(Review review : model.getReviews()){
            reviewDTOs.add(review.createDTO(review));
        }
        dto.setReviewSet(reviewDTOs);
        return dto;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
