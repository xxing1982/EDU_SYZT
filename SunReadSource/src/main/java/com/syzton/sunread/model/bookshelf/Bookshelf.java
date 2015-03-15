package com.syzton.sunread.model.bookshelf;

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.bookshelf.BookshelfDTO;

import javax.persistence.*;

/**
 * @author Morgan-Leon
 */
@Entity
@Table(name="bookshelf")
public class Bookshelf {
   
    public static final int MAX_LENGTH_OWNER = 100;
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

    /*
     * ￼
	"mapperBy" is used in embedded class. eg. person & person_detail.
	"JoinColumn" is used in  reference.
     */
    //@OneToOne(optional = false, cascade = CascadeType.ALL)
    //@JoinColumn(name = "owner", referencedColumnName = "user_id", unique = true)
    private long owner;
    

    /*
     *@OneToMany(mappedBy="order",cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	 *@OrderBy(value = "id ASC") 
     *
     */

    @OneToMany( cascade = {CascadeType.MERGE,CascadeType.REFRESH},mappedBy = "bookshelf")
    @Basic(fetch = FetchType.EAGER)
    private Collection<BookInShelf> booksInShelf;
    
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "bookshelf")
    @Basic(fetch = FetchType.LAZY)
    private Collection<BookShelfOperation> bookShelfOprations ;
        
    
    public Bookshelf() {

    }

    public static Builder getBuilder(long owner) {
    	return new Builder(owner);
		
	}
    //how to deal with  name  duplication ?
//    public static Builder getBuilder(long owner,Collection<BookInShelf> booksInShelf, 
//    		Collection<BookShelfOperation> bookShelfOperations) {
//        return new Builder(owner,booksInShelf,bookShelfOperations);
//    }
    

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

    public Collection<BookInShelf> getBooksInShelf(){    	
    	return booksInShelf;
    }
    
    public Collection<BookShelfOperation> getBookShelfOperations() {
		// TODO Auto-generated method stub
		return bookShelfOprations;
	}
    public Long getOwner() {
		return owner;
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

//    public void update(String description, String title) {
//        this.description = description;
//    }

    public static class Builder {

        private Bookshelf built;

        public Builder(long owner){
        	built = new Bookshelf();
        	built.owner = owner;       	
        }
        
        
//        public Builder(long owner,Collection<BookInShelf> booksInShelf, Collection<BookShelfOperation> bookShelfOperations) {
//            built = new Bookshelf();
//            built.owner = owner;
//            built.booksInShelf = booksInShelf;
//            built.bookShelfOprations = bookShelfOperations;
//        }

        //Build booksInShelfhelf by owner name(:String)

        public Bookshelf build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
        
        public Builder booksInShelf(Collection<BookInShelf> bookInShelfs) {	
        	built.booksInShelf = bookInShelfs;
			return this;
		}
        
        public Builder bookShelfOperations(Collection<BookShelfOperation> bookShelfOperations) {	
        	built.bookShelfOprations = bookShelfOperations;
			return this;
		}
    }
    
    public BookshelfDTO createDTO(Bookshelf model){
    	BookshelfDTO dto = new BookshelfDTO();
    	dto.setOwner(model.getOwner());
//    	dto.setBooksInShelf(model.getBooksInShelf());
//    	dto.setBookShelfOperations(model.getBookShelfOperations());
    	dto.setDescription(model.getDescription());
		return dto;
    }



	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

