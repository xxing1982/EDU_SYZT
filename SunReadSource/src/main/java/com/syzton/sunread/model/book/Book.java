package com.syzton.sunread.model.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry zhang
 */
@Entity
@Table(name="book")
@JsonIgnoreProperties(value = { "reviews","categories" })
public class Book extends AbstractEntity{

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    public static final int MAX_LENGTH_ISBN = 20;


    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @Column(name="name",nullable = false,length = MAX_LENGTH_NAME)
    private String name;

    @Column(name ="isbn",unique = true,nullable = false,length = MAX_LENGTH_ISBN)
    private String isbn;

    @Column(name = "avg_rate")
    private int avgRate;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "book")
    @Basic(fetch = FetchType.LAZY)
    private Set<Review> reviews = new HashSet<>() ;


    @ManyToMany
    @JoinTable(name="book_category",
            joinColumns = @JoinColumn(name="book_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName="id")
    )
    private Set<Category> categories = new HashSet<>();


    public Book() {

    }

    public static Builder getBuilder(String isbn,String name) {
        return new Builder(isbn,name);
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

    public String getIsbn() {
        return isbn;
    }


    public int getAvgRate() {
        return avgRate;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    public void setModificationTime(DateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAvgRate(int avgRate) {
        this.avgRate = avgRate;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

        private Book built;

        public Builder(String isbn,String name) {
            built = new Book();
            built.name = name;
            built.isbn = isbn;
        }

        public Book build() {
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
    public BookDTO createDTO() {
        BookDTO dto = new BookDTO();
        dto.setId(this.getId());
        dto.setName(this.getName());
        dto.setIsbn(this.getIsbn());
        dto.setDescription(this.getDescription());
        //  dto.setPublicationDate(new Date());
        // dto.setReviewSet(model.getReviews());
        return dto;
    }
    public BookDTO bookWithReview(){
       BookDTO dto = this.createDTO();
        Set<ReviewDTO> reviewDTOs = new HashSet<>();
        for(Review review : this.getReviews()){
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
