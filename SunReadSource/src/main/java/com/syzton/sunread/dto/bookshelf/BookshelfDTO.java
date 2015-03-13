package com.syzton.sunread.dto.bookshelf;

import java.util.Collection;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import antlr.collections.List;

import com.syzton.sunread.dto.book.ReviewDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.BookShelfOperation;
import com.syzton.sunread.model.bookshelf.Bookshelf;


/**
 * @author Morgan-Leon
 */
public class BookshelfDTO {

    private Long id;

    @Length(max = Bookshelf.MAX_LENGTH_DESCRIPTION)
    private String description;
    
    private DateTime creation_time;
    private DateTime modification_time;
    
    private Long owner;
    
    private Collection<BookInShelf> booksInShelf ;
    
    private Collection<BookShelfOperation> bookShelfOperations;
    
    public BookshelfDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public DateTime getCreationTime() {
        return creation_time;
    }

    public void setCreationTime(DateTime create_time) {
        this.creation_time = create_time;
    }
    
    public DateTime getModificationTime() {
        return modification_time;
    }

    public void setModificationTime(DateTime modification_time) {
        this.modification_time = modification_time;
    }

    
    public Long getOwner() {
		return owner;
	}
    public void setOwner(Long owner) {
		this.owner = owner;
	}
    
    public Collection<BookInShelf> getBooksInShelf() {
        return booksInShelf;
    }

    public void setBooksInShelf(Collection<BookInShelf> booksInShelf) {
        this.booksInShelf = booksInShelf;
    }
   
    public Collection<BookShelfOperation> getBookShelfOperations() {
        return bookShelfOperations;
    }

    public void setBookShelfOperations(Collection<BookShelfOperation> bookShelfOperations) {
        this.bookShelfOperations= bookShelfOperations;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}