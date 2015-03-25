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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.bookshelf.BookShelfOperationDTO;
import com.syzton.sunread.util.DateSerializer;


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

    @JsonSerialize(using = DateSerializer.class)
    @Column(name = "modification_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH },optional=false)
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;
    
    @Column(name = "operation_type",nullable = false)
    private OperationType operationType;
    
//    @Column(name = "operator", nullable = false)
//    private long user;
//	  operationTime = creation_time
    
    public BookShelfOperation() {
    }
    
    
    public static Builder getBuilder(Bookshelf bookshelf, OperationType operationType) {
        return new Builder(bookshelf, operationType);
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
    
    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getModificationTime() {
        return modificationTime;
    }

    
    public Bookshelf getBookshelf() {
    	return bookshelf;
	}
    public OperationType getOperationType(){
    	return operationType;
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

        private BookShelfOperation  built;
        
        public BookShelfOperation build() {
            return built;
        }

        public Builder description(String description) {
            built.description = description;
            return this;
        }
        
        public Builder(Bookshelf bookshelf,OperationType operationType) {           
        	built = new BookShelfOperation();
        	built.operationType= operationType;
        	built.bookshelf = bookshelf;
        }

    }
    
    public BookShelfOperationDTO createDTO(BookShelfOperation model) {
		BookShelfOperationDTO dto =new BookShelfOperationDTO();
		dto.setBookshelf(model.getBookshelf());
		dto.setCreationTime(model.getCreationTime().getMillis());
		dto.setDescription(model.getDescription());
		dto.setId(model.getId());
		dto.setModificationTime(model.getModificationTime().getMillis());
		dto.setOperationType(model.getOperationType());
		
		return dto;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
