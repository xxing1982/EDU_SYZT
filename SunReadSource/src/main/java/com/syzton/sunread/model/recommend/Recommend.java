/**
 * 
 */
package com.syzton.sunread.model.recommend;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.dto.recommend.RecommendDTO;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.BookInShelf.Builder;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.util.DateSerializer;

/**
 * @author Morgan-Leon
 * @Date 2015年5月12日
 * 
 */
@Entity
@Table(name="recommend")
@JsonIgnoreProperties(value = {""})
public class Recommend extends AbstractEntity{
	
    public static final int MAX_LENGTH_DESCRIPTION = 500;
	
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "teacher")
    private Teacher teacher;
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name ="bookinshelf")
    private BookInShelf bookinshelf;
	
    @Column(nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;

    @JsonSerialize(using = DateSerializer.class)
    @Column(nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public BookInShelf getBookinshelf() {
		return bookinshelf;
	}

	public void setBookinshelf(BookInShelf bookinshelf) {
		this.bookinshelf = bookinshelf;
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

	public void setModificationTime(DateTime modificationTime) {
		this.modificationTime = modificationTime;
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
    
    public static Builder getBuilder(Teacher teacher, BookInShelf bookInShelf ) {
		return new Builder(teacher, bookInShelf);
	}
    
    public static class Builder {
    	private Recommend built;
    	
    	
    	public Builder(Teacher teacher,BookInShelf bookInShelf){
    		built = new Recommend();
    		built.teacher = teacher;
    		built.bookinshelf = bookInShelf;
    	}
    	
    	public Recommend build(){
    		return built;
    	}
		public Builder description(String description) {
            built.description = description;
            return this;
        }
    }
    
}
