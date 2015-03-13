package com.syzton.sunread.model.supplementbook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.dto.supplementbook.SupplementBookDTO;
import com.syzton.sunread.model.book.Review;
import com.syzton.sunread.util.DateSerializer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Morgan-Leon
 */
@Entity
@Table(name="supplementbook")
@JsonIgnoreProperties(value = { "reviews" })
public class SupplementBook {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;
    public static final int MAX_LENGTH_ISBN = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonSerialize(using = DateSerializer.class)

    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

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


    public SupplementBook() {

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

        private SupplementBook built;

        public Builder(String isbn,String name) {
            built = new SupplementBook();
            built.name = name;
            built.isbn = isbn;
        }

        public SupplementBook build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
    }
    
    public SupplementBookDTO createDTO(SupplementBook model) {
        SupplementBookDTO dto = new SupplementBookDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setIsbn(model.getIsbn());
        dto.setDescription(model.getDescription());
        //  dto.setPublicationDate(new Date());
        // dto.setReviewSet(model.getReviews());
        return dto;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
