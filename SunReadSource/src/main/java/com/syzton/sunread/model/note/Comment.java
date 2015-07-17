package com.syzton.sunread.model.note;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.User;


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

	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="note_id")
    private Note note;
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = true)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
	private User user;
    
    
    public Comment() {

    }

    public Long getId() {
        return id;   
    }

	public String getContent() {
		return content;
	}
	
	
	public void setContent(String content) {
		this.content = content;
	}


	public Note getNote() {
		return this.note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername(){
		return user.getUsername();
	}
	
}
