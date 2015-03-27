package com.syzton.sunread.dto.bookshelf;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.syzton.sunread.model.bookshelf.Bookshelf;


/**
 * @author Morgan-Leon
 */
public class BookshelfDTO {

    private Long id;

    @Length(max = Bookshelf.MAX_LENGTH_DESCRIPTION)
    private String description;
    
    private Long creation_time;
    
    private Long modification_time;
    
    private Long owner;
    
    private int unreadMust;
    
    private int unreadSelect;
    
    private int readMust;
    
    private int readSelect;
   
//    private Collection<BookInShelf> booksInShelf ;
//    
//    private Collection<BookShelfOperation> bookShelfOperations;
    
    public int getUnreadMust() {
		return unreadMust;
	}

	public void setUnreadMust(int unreadMust) {
		this.unreadMust = unreadMust;
	}

	public int getUnreadSelect() {
		return unreadSelect;
	}

	public void setUnreadSelect(int unreadSelect) {
		this.unreadSelect = unreadSelect;
	}

	public int getReadMust() {
		return readMust;
	}

	public void setReadMust(int readMust) {
		this.readMust = readMust;
	}

	public int getReadSelect() {
		return readSelect;
	}

	public void setReadSelect(int readSelect) {
		this.readSelect = readSelect;
	}

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
    
    public Long getCreationTime() {
        return creation_time;
    }

    public void setCreationTime(Long create_time) {
        this.creation_time = create_time;
    }
    
    public Long getModificationTime() {
        return modification_time;
    }

    public void setModificationTime(Long modification_time) {
        this.modification_time = modification_time;
    }
    

    
    public Long getOwner() {
		return owner;
	}
    public void setOwner(Long owner) {
		this.owner = owner;
	}
    
//    public Collection<BookInShelf> getBooksInShelf() {
//        return booksInShelf;
//    }
//
//    public void setBooksInShelf(Collection<BookInShelf> booksInShelf) {
//        this.booksInShelf = booksInShelf;
//    }
//   
//    public Collection<BookShelfOperation> getBookShelfOperations() {
//        return bookShelfOperations;
//    }
//
//    public void setBookShelfOperations(Collection<BookShelfOperation> bookShelfOperations) {
//        this.bookShelfOperations= bookShelfOperations;
//    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}