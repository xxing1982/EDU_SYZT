package com.syzton.sunread.model.note;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.User;
import javax.persistence.*;
import java.util.Set;


/**
 * @author chenty
 *
 */
@Entity
@Table(name="note")
public class Note extends AbstractEntity{

    public static final int MAX_LENGTH_TITLE = 200;
    public static final int MAX_LENGTH_CONTENT = 200000;
    public static final int MAX_LENGTH_IMAGE = 10485760; // 10MiB
    

	@Column(name="title",length = MAX_LENGTH_TITLE)
    private String title;
    
    @Column(name="content",length = MAX_LENGTH_CONTENT)
    private String content;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "note")
    @Basic(fetch = FetchType.LAZY)
    private Set<Comment> comments;
    
    @Column(name="comment_count")
    private Long commentCount = 0L;
    
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
	private Book book;

    @Column(name="image",length = MAX_LENGTH_IMAGE)
    private String image;
    
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;
    
    public Note() {

    }

    public static Builder getBuilder(String title, String content, Book book) {
        return new Builder(title, content, book);
    }

    public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

    public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getImage() {
		return image;
	}
    
    public void update(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    
    public static class Builder {

        private Note built;

        public Builder(String title, String content, Book book) {
            built = new Note();
            built.title = title;
            built.content = content;
            built.book = book;
        }

        public Note build() {
            return built;
        }

        public Builder image(String image){
        	built.image = image;
        	return this;
        }
    }

    public NoteDTO createDTO(Note model) {
        NoteDTO dto = new NoteDTO();

        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setContent(model.getContent());
        dto.setImage(model.getImage());
        dto.setCommentCount(model.getCommentCount());

        return dto;
    }

    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
