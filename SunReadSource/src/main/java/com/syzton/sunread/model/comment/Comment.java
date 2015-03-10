package com.syzton.sunread.model.comment;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

import java.util.Collection;


/**
 * @author chenty
 *
 */
@Entity
@Table(name="Comment")
public class Comment {

    public static final int MAX_LENGTH_CONTENT = 200000;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="content",length = MAX_LENGTH_CONTENT)
    private String content;

    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;
    
    public Comment() {

    }

    public static Builder getBuilder(String content) {
        return new Builder(content);
    }

    public Long getId() {
        return id;   
    }

	public DateTime getCreationTime() {
		return creationTime;
	}

	public String getContent() {
		return content;
	}

    public void update(String content) {
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
    }

    
    public static class Builder {

        private Comment built;

        public Builder(String content) {
            built = new Comment();
            built.content = content;
        }

        public Comment build() {
            return built;
        }

//        public Builder image(String image){
//        	built.image = image;
//        	return this;
//        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
