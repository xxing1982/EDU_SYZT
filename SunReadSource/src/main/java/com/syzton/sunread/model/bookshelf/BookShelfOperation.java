package com.syzton.sunread.model.bookshelf;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf.Builder;


/**
 * @Date 2015/3/12
 * @author Morgan-Leon
 */
@Entity
@Table(name="bookshelfoperation")

public class BookShelfOperation {
    
	public static final int MAX_LENGTH_DESCRIPTION = 500;
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
    
    //user operation_time operation_type
    
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH },optional=false)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;
    
    public BookShelfOperation() {

    }
    
    public Bookshelf getBookshelf() {
		return bookshelf;
	}
    

    public static Builder getBuilder() {
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

        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
