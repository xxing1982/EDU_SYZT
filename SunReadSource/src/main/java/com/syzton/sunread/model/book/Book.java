package com.syzton.sunread.model.book;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jerry zhang
 */
@Entity
@Table(name="book")
public class Book {

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

    @Column(name ="isbn",unique = true,nullable = false,length = MAX_LENGTH_ISBN)
    private String isbn;

    @Column(name = "avg_rate")
    private int avgRate;

//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "book_id")
//    @Basic(fetch = FetchType.LAZY)
//    private Collection<Review> reviews = new ArrayList<>() ;






    public Book() {

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

    public String getIsbn() {
        return isbn;
    }


    public int getAvgRate() {
        return avgRate;
    }

//    public Collection<Review> getReviews() {
//        return reviews;
//    }

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

//        public Builder reviews(Collection<Review> reviews){
//            built.reviews = reviews;
//            return this;
//        }


    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
