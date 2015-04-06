package com.syzton.sunread.model.supplementbook;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.supplementbook.SupplementBookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Morgan-Leon
 */
@Entity
@Table(name="supplementbook")
public class SupplementBook extends AbstractEntity{

    @Column(name = "description", nullable = true, length = Book.MAX_LENGTH_DESCRIPTION)
    private String description;

    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @Column(nullable = false,length = Book.MAX_LENGTH_AUTHOR)
    private String author;
    
    @Column(name="name",nullable = false,length = Book.MAX_LENGTH_NAME)
    private String name;
    
    @Column(nullable = false,length = Book.MAX_LENGTH_PUBLISHER)
    private String publisher;
    
    @JsonSerialize(using = DateSerializer.class)
    @Column(nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime publicationDate;
   
    private int language;

	@Column(name ="isbn",unique = true,nullable = false,length = Book.MAX_LENGTH_ISBN)
    private String isbn;


    public SupplementBook() {

    }

    public static Builder getBuilder(int language ,String author,String publisher,DateTime publishDate,String isbn,String name) {
        return new Builder(language,author,publisher,publishDate, isbn, name);
    }

    public String getDescription() {
        return description;
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }


    public String getAuthor() {
		return author;
	}

	public String getName() {
		return name;
	}

	public String getPublisher() {
		return publisher;
	}

	public DateTime getPublicationDate() {
		return publicationDate;
	}

	public String getIsbn() {
		return isbn;
	}
	
    public int getLanguage() {
		return language;
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

        public Builder(int language,String author,String publisher,DateTime publishDate,String isbn,String name) {
            built = new SupplementBook();
            built.language = language;
            built.author=author;
            built.publisher = publisher;
            built.publicationDate = publishDate;
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
        dto.setAuthor(model.getAuthor());
        dto.setPublisher(model.getPublisher());
        dto.setLanguage(model.getLanguage());
        dto.setDescription(model.getDescription());
        dto.setPublicationDate(model.getPublicationDate().getMillis());
        return dto;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
