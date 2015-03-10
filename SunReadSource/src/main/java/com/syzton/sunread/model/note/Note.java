package com.syzton.sunread.model.note;
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
@Table(name="Note")
public class Note {

    public static final int MAX_LENGTH_TITLE = 200;
    public static final int MAX_LENGTH_CONTENT = 200000;
    public static final int MAX_LENGTH_IMAGE = 10485760; // 10MiB
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@Column(name="title",length = MAX_LENGTH_TITLE)
    private String title;
    
    @Column(name = "creation_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    @Column(name="content",length = MAX_LENGTH_CONTENT)
    private String content;

    @Column(name="image",length = MAX_LENGTH_IMAGE)
    private String image;
    
    public Note() {

    }

    public static Builder getBuilder(String title, String content) {
        return new Builder(title, content);
    }

    public Long getId() {
        return id;   
    }

    public String getTitle() {
		return title;
	}

	public DateTime getCreationTime() {
		return creationTime;
	}

	public String getContent() {
		return content;
	}

	public String getImage() {
		return image;
	}
    
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
    }

    
    public static class Builder {

        private Note built;

        public Builder(String title, String content) {
            built = new Note();
            built.title = title;
            built.content = content;
        }

        public Note build() {
            return built;
        }

        public Builder image(String image){
        	built.image = image;
        	return this;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
