package com.syzton.sunread.model.note;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.note.CommentDTO;

import javax.persistence.*;

import java.util.Collection;


/**
 * @author chenty
 *
 */
@Entity
@Table(name="comment")
public class Comment {

    public static final int MAX_LENGTH_CONTENT = 200000;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="content",length = MAX_LENGTH_CONTENT)
    private String content;

    @Column(name = "create_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;
    
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="note_id")
    private Note note;
    
    public Comment() {

    }

    public static Builder getBuilder(String content, Note note) {
        return new Builder(content, note);
    }

    public Long getId() {
        return id;   
    }

	public DateTime getCreateTime() {
		return createTime;
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
        createTime = now;
    }

    
    public static class Builder {

        private Comment built;

        public Builder(String content, Note note) {
            built = new Comment();
            built.content = content;
            built.note = note;
        }

        public Comment build() {
            return built;
        }

//        public Builder image(String image){
//        	built.image = image;
//        	return this;
//        }
    }

    public CommentDTO createDTO(Comment model) {
        CommentDTO dto = new CommentDTO();

        dto.setId(model.getId());
        dto.setContent(model.getContent());
        return dto;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
