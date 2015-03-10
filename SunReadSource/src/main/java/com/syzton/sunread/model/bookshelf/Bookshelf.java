package com.syzton.sunread.model.bookshelf;

import java.util.Collection;


import com.syzton.sunread.model.book.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


import javax.persistence.*;

/**
 * @author Morgan-Leon
 */
@Entity
@Table(name="BOOKSHELF")
public class Bookshelf {
    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;
    public static final int MAX_LENGTH_OWNER = 100;
    public static final int MAX_LENGTH_OPERATION_RECORD = 1000;

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
    @OneToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "owner", referencedColumnName = "user_id", unique = true)
    private long owner;
    
    @Column(name = "operation_record", nullable = true, length = MAX_LENGTH_OPERATION_RECORD)
    private String operation_record;
    
    /*
     *@OneToMany(mappedBy="order",cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
	 *@OrderBy(value = "id ASC")  
     *
     */
    @OneToMany(mappedBy = "bookshelf", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(value = "createTime")
    private Collection<Book> books;

    public Bookshelf() {

    }

    //how to deal with  name  duplication ?
    public static Builder getBuilder(long owner) {
        return new Builder(owner);
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

    public String getTitle() {
        return operation_record;
    }
    
    public Collection<Book> getBooks(){    	
    	return books;
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
        	built.operation_record = "";
        	//built.books = null;   	
        }
        
        public Builder(String owner_name){        	
        }

        public Builder(long owner,Collection<Book> books, String operation_record) {
            built = new Bookshelf();
            built.owner = owner;
            built.books = books;
            built.operation_record = operation_record;
        }
        
        //Build bookshelf by owner name(:String)
        public Builder(String owner_name,Collection<Book> books, String operation_record) {
        }

        
        public Bookshelf build() {
            return built;
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

