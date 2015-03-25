package com.syzton.sunread.dto.note;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.syzton.sunread.model.note.Comment;


/**
 * @author chenty
 *
 */
public class CommentDTO {

	private Long id;
    
    @NotEmpty
    @Length(max = Comment.MAX_LENGTH_CONTENT)
	private String content;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
