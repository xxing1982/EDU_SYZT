package com.syzton.sunread.model.bookshelf;

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.model.book.Book;

import javax.persistence.*;

/**
 * @author Morgan-Leon
 *
 */
@Entity
@Table(name="BOOKINSHELF")
public class BookInShelf {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_NAME = 100;

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
    //

    //is required or optional
    @Column(name = "isMandatory", nullable = false)
    private boolean isMandatory;

//    @Column(name = "isVerified", nullable = false)
//    private boolean isVerified;

    //private enum readState{read, reading, unread};

    //read = 1 or unread = 0, reading = 3?
    @Column(name = "readState", nullable = false)
    private int readState;


    //a bookshelf can`t have the same books
    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf_id;

    @ManyToOne(cascade=CascadeType.REFRESH,optional=false)
    @JoinColumn(name = "book_id")
    private Book book_id;

    public BookInShelf() {

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


    public int getReadState() {
        return readState;
    }

    public boolean getBookAttribute() {
        return isMandatory;
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
        	built.readState = 0;
        	built.isMandatory = false;
		}

        public Builder(Book book_id, Bookshelf bookshelf_id,
        		int readState, boolean isMandatory) {
        	built = new BookInShelf();
        	built.book_id = book_id;
        	built.bookshelf_id = bookshelf_id;
        	built.readState = readState;
        	built.isMandatory = isMandatory;

        }

		public Builder description(String description) {
            built.description = description;
            return this;
        }


    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}


