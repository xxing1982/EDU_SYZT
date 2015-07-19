package com.syzton.sunread.model.bookshelf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.bookshelf.BookshelfDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.util.DateSerializer;

/**
 * @author Morgan-Leon
 */
@Entity
@Table(name="bookshelf")
@JsonIgnoreProperties(value = {"booksInShelf"})
public class Bookshelf {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;

    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

	
    public static final int MAX_LENGTH_DESCRIPTION = 500;


    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;
    
    private String username;

    @OneToMany( cascade = {CascadeType.ALL},mappedBy = "bookshelf")
    @Basic(fetch = FetchType.LAZY)
    private List<BookInShelf> booksInShelf = new ArrayList<BookInShelf>();
    
//    @OneToMany( cascade = CascadeType.ALL, mappedBy = "bookshelf")
//    @Basic(fetch = FetchType.EAGER)
//    private Set<BookShelfOperation> bookShelfOprations ;
        
    
    public Bookshelf() {

    }
    

    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public DateTime getCreationTime() {
		return creationTime;
	}


	public void setCreationTime(DateTime creationTime) {
		this.creationTime = creationTime;
	}


	public static Builder getBuilder(String username,Long id) {
    	return new Builder(username, id);
		
	}

    public DateTime getModificationTime() {
        return modificationTime;
    }

    public List<BookInShelf> getBooksInShelf(){    	
    	return booksInShelf;
    }
	public void setBookInShelf(ArrayList<BookInShelf> bookSet) {
		// TODO Auto-generated method stub
		this.booksInShelf = bookSet;
	}
	
	public String getUsername() {
		return username;
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

        public Builder(String username,Long userId){
        	built = new Bookshelf();
        	built.username = username; 
        	built.id = userId;
        }


        public Bookshelf build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
       
    }
    
    public BookshelfDTO createDTO(Bookshelf model){
    	BookshelfDTO dto = new BookshelfDTO();
    	int unreadMust = 0, unreadSelect = 0;
    	int readMust = 0, readSelect = 0;
    	dto.setId(model.getId());
    	dto.setCreationTime(model.getCreationTime().getMillis());
    	dto.setModificationTime(model.getModificationTime().getMillis());
    	dto.setStudentId(model.getId());
    	dto.setUsername(model.getUsername());
    	dto.setDescription(model.description);
    	
    	if (model.booksInShelf != null) {
			for (BookInShelf bookInShelf:model.booksInShelf) {
				//isVerified == true read
				if (bookInShelf.getReadState() == true) {
					//isMandatory == true must
					if (bookInShelf.getBookAttribute() == true) 
						readMust++;
					//isMandatory == false Select
					else 
						readSelect++;
				}
				//isVerified == false unread
				else {
					//isMandatory == ture must
					if (bookInShelf.getBookAttribute() == true) 
						unreadMust++;
					else
						unreadSelect++;
				}
			}
		}
    	else {
			throw new NotFoundException("no books in this bookShelf with id"+ model.getId());
		}
    	
    	dto.setReadMust(readMust);
    	dto.setReadSelect(readSelect);
    	dto.setUnreadMust(unreadMust);
    	dto.setUnreadSelect(unreadSelect);
    	dto.setBooksInShelf(model.getBooksInShelf());
    	
    	
		return dto;
    }

    


	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }



}

