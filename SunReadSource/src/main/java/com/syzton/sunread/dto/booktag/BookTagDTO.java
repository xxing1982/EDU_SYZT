package com.syzton.sunread.dto.booktag;

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
//    
//	@NotEmpty
//    @Length(max = Tag.MAX_LENGTH_NAME)
//	private String book_id;
//
//
//    @NotEmpty
//    @Length(max = Tag.MAX_LENGTH_VALUE)
//    private String tag_id;
//
//
//    public String getBook_id() {
//		return book_id;
//	}
//
//	public void setBook_id(String book_id) {
//		this.book_id = book_id;
//	}
//
//	public String getTag_id() {
//		return tag_id;
//	}
//
//	public void setTag_id(String tag_id) {
//		this.tag_id = tag_id;
//	}
//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
