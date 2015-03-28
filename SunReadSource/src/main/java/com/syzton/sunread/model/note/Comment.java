package com.syzton.sunread.model.note;
import com.syzton.sunread.dto.note.CommentDTO;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.User;
import javax.persistence.*;


/**
 * @author chenty
 *
 */
@Entity
@Table(name="comment")
public class Comment extends AbstractEntity {

    public static final int MAX_LENGTH_CONTENT = 200000;
    
    @Column(name="content",length = MAX_LENGTH_CONTENT)
    private String content;

	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="note_id")
    private Note note;
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;
    
    
    public Comment() {

    }

    public static Builder getBuilder(String content, Note note) {
        return new Builder(content, note);
    }

    public Long getId() {
        return id;   
    }

	public String getContent() {
		return content;
	}

    public void update(String content) {
        this.content = content;
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
    }

    public CommentDTO createDTO(Comment model) {
        CommentDTO dto = new CommentDTO();

        dto.setId(model.getId());
        dto.setContent(model.getContent());
        return dto;
    }
}
