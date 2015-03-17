package com.syzton.sunread.model.bookshelf;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.h2.command.dml.Update;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.util.DateSerializer;

import javax.persistence.*;

/**
 * @author Morgan-Leon
 *
 */
@Entity
@Table(name="bookinshelf")
public class BookInShelf {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;

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
    //

    //is required or optional
    @Column(name = "isMandatory", nullable = false)
    private boolean isMandatory;

    @Column(name = "isVerified", nullable = false)
    private boolean isVerified;
    
    //a bookshelf can`t have the same books
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH },optional=false)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookInShelf() {

    }

    public static Builder getBuilder() {
        return new Builder();
    }
    public static Builder getBuilder(Book book ,Bookshelf bookshelf, boolean isMandatory,boolean isVerified) {
        return new Builder();
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

    public boolean getBookAttribute() {
        return isMandatory;
    }

    public boolean getReadState() {
        return isVerified;
    }

    public Book getBook() {
        return book;
    }
    
    public Bookshelf getBookShelf(){
    	return bookshelf;
    }
    
    public void update(String description,boolean isManditory
    		,boolean isVerified){
    	this.description = description;
    	this.isMandatory = isManditory;
    	this.isVerified = isVerified;
    	this.modificationTime = DateTime.now();
    	
    }

    //getBook & getBookShelf

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

        private BookInShelf built;

        public Builder() {
			// TODO Auto-generated constructor stub
        	built = new BookInShelf();
        	//book
        	//bookshelf
        	built.isVerified = false;
        	built.isMandatory = false;
		}
        
        public BookInShelf build() {
            return built;
        }
        
        public Builder(Book book, Bookshelf bookshelf, 
        		boolean isVerified, boolean isMandatory) {           
        	built = new BookInShelf();
        	built.book = book;
        	built.bookshelf = bookshelf;
        	built.isVerified = isVerified;
        	built.isMandatory = isMandatory;

        }

		public Builder description(String description) {
            built.description = description;
            return this;
        }


    }
    
    public BookInShelfDTO createDTO(BookInShelf model){
    	BookInShelfDTO dto = new BookInShelfDTO();
    	dto.setId(model.getId());
    	dto.setBookAttribute(model.getBookAttribute());
    	dto.setReadState(model.getReadState());
    	dto.setBook(model.getBook());
    	dto.setBookshelf(model.getBookShelf());
    	return dto;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}


