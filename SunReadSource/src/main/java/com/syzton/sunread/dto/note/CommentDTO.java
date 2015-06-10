package com.syzton.sunread.dto.note;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.User;

/**
 * @author chenty
 *
 */

public class CommentDTO extends AbstractEntity {

    public static final int MAX_LENGTH_CONTENT = 200000;
    
    private String content;
	
	private User user;
    
    public CommentDTO() {

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

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername(){
		return user.getUsername();
	}	
}
