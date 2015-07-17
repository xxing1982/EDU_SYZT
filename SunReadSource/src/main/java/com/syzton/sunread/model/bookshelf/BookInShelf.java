package com.syzton.sunread.model.bookshelf;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.bookshelf.BookInShelfDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.util.DateSerializer;

/**
 * @author Morgan-Leon
 *
 */
@Entity
@Table(name="bookinshelf")
@JsonIgnoreProperties(value = {"bookshelf"})
public class BookInShelf extends AbstractEntity{

    public static final int MAX_LENGTH_DESCRIPTION = 500;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;
   
    //six book Attributes
    private Long bookId;
    
    @Column(name="book_name",nullable = false,length = Book.MAX_LENGTH_NAME)
    private String bookName;

    @Column(name ="isbn",nullable = false,length = Book.MAX_LENGTH_ISBN)
    private String isbn;
    
    @Column(nullable = false,length = Book.MAX_LENGTH_AUTHOR)
    private String author;
    
    private String pictureUrl = "ftp://default_book_picture";
    
    private int point = Book.DEFAULT_POINT;
    //end Book Attributes
    
    //is required or optional
    @Column(name = "isMandatory", nullable = false)
    private boolean isMandatory;

    @Column(name = "isVerified", nullable = false)
    private boolean isVerified;
    
    //a bookshelf can`t have the same books
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},optional=false)
    @JoinColumn(name = "bookshelf")
    private Bookshelf bookshelf;
    
    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "verified_time", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime verifiedTime;

//    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH },optional=false)
//    @JoinColumn(name = "book")
//    private Book book;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
    
    public BookInShelf() {

    }

    public static Builder getBuilder() {
        return new Builder();
    }
    
    public static Builder getBuilder(Long bookId,String bookName,String isbn,String pictureUrl
    		,String author,int point,Bookshelf bookshelf
    		,boolean isMandatory,boolean isVerified) {
    	
        return new Builder(bookId,bookName,isbn,pictureUrl,author,point,bookshelf,isMandatory,isVerified);
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
    
    public boolean getDeleted() {
        return deleted;
    }
    
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    

    public Long getBookId() {
		return bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getAuthor() {
		return author;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public int getPoint() {
		return point;
	}

	public boolean getBookAttribute() {
        return isMandatory;
    }
	
	public void setBookAttribute(boolean bookAttribute) {
        this.isMandatory =  bookAttribute;
    }

    public boolean getReadState() {
        return isVerified;
    }

    public Bookshelf getBookShelf(){
    	return bookshelf;
    }
    
    
    
    public DateTime getVerifiedTime() {
		return verifiedTime;
	}

	public void update(String description,boolean isManditory
    		,boolean isVerified){
    	this.description = description;
    	this.isMandatory = isManditory;
    	this.isVerified = isVerified;
    	this.modificationTime = DateTime.now(); 	
    }
	
	public boolean updateReadState(){
    	if (!isVerified) {
    		this.isVerified = true;
    		this.verifiedTime = DateTime.now();
    		return true;
		}
    	else {
    		return false;
		}
    }
	
	public boolean updateByBook(Book book) {
		if (this.bookId == book.getId()) {
			this.author = book.getAuthor();
			this.bookName = book.getName();
			this.isbn = book.getIsbn();
			this.pictureUrl = book.getPictureUrl();
			this.point = book.getPoint();	
			return true;
		}
		else {
			return false;
		}	
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
        
        public Builder(Long bookId,String bookName,String isbn,String pictureUrl
        		,String author,int point,Bookshelf bookshelf
        		,boolean isMandatory,boolean isVerified) {           
        	built = new BookInShelf();
        	built.bookId = bookId;
        	built.bookName = bookName;
        	built.isbn = isbn;
        	built.pictureUrl = pictureUrl;
        	built.author = author;
        	built.point = point;
        	built.bookshelf = bookshelf;
        	built.isVerified = isVerified;
        	built.isMandatory = isMandatory;
        }

		public Builder description(String description) {
            built.description = description;
            return this;
        }


    }
    
    public BookInShelfDTO createDTO(){
    	BookInShelfDTO dto = new BookInShelfDTO();
    	dto.setId(id);
    	dto.setCreateTime(creationTime.getMillis());
    	dto.setDescription(description);
    	dto.setModificationTime(modificationTime.getMillis());
    	dto.setBookAttribute(isMandatory);
    	dto.setReadState(isVerified);
    	dto.setBookIsbn(isbn);
    	dto.setBookshelf(bookshelf.getId());
    	return dto;
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}


