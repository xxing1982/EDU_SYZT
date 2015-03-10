package com.syzton.sunread.model.booktag;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author chenty
 *
 */
@Entity
@Table(name="Booktag")
public class BookTag {

    public static final int MAX_LENGTH_NAME = 20;
    public static final int MAX_LENGTH_VALUE = 20;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//	@ManyToOne
//	@JoinColumn(name="book_id", 
//				insertable=false, updatable=false, 
//				nullable=false)
//	private Book book;
    
    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

//	@ManyToOne
//	@JoinColumn(name="tag_id", 
//				insertable=false, updatable=false, 
//				nullable=false)
//	private Tag tag;


    public BookTag() {

    }

    public static Builder getBuilder() {
    	
        return new Builder();
    }

    public Long getId() {
        return id;   
    }
    
    public void update() {
    }

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
    }

    
    public static class Builder {

        private BookTag built;

        public Builder() {
            built = new BookTag();
        }

        public BookTag build() {
            return built;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
