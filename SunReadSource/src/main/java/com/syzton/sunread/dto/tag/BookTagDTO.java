package com.syzton.sunread.dto.tag;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.syzton.sunread.model.tag.Tag;
import com.syzton.sunread.todo.model.Todo;


/**
 * @author chenty
 *
 */
public class BookTagDTO {
	
    private Long id;
    
	private Long book_id;

    private Long tag_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Long getBook_id() {
		return book_id;
	}
    
	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public Long getTag_id() {
		return tag_id;
	}

	public void setTag_id(Long tag_id) {
		this.tag_id = tag_id;
	}
}
