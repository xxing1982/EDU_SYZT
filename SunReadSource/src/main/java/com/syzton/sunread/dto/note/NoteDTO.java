package com.syzton.sunread.dto.note;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.todo.model.Todo;


/**
 * @author chenty
 *
 */
public class NoteDTO {
	
    private Long id;

	@NotEmpty
    @Length(max = Note.MAX_LENGTH_TITLE)
	private String title;


    @NotEmpty
    @Length(max = Note.MAX_LENGTH_CONTENT)
    private String content;

    
    @Length(max = Note.MAX_LENGTH_IMAGE)
    private String image;
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
