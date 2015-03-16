package com.syzton.sunread.model.tag;
import com.syzton.sunread.dto.tag.BookTagDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.common.AbstractEntity;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author chenty
 *
 */
@Entity
@Table(name="booktag")
public class BookTag extends AbstractEntity{

	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
	private Book book;

	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="tag_id")
	private Tag tag;
	

    public BookTag() {

    }

    public static Builder getBuilder(Tag tag, Book book) {
    	
        return new Builder(tag, book);
    }
    
    public Long getTag_Id() {
		return tag.getId();
	}
    
    public Long getBook_Id() {
    	return book.getId();
    }

	public void update() {
    }

    public static class Builder {

        private BookTag built;

        public Builder(Tag tag, Book book) {
            built = new BookTag();
            built.tag = tag;
            built.book = book;
        }

        public BookTag build() {
            return built;
        }
    }

    public BookTagDTO createDTO(BookTag model) {
        BookTagDTO dto = new BookTagDTO();
        dto.setId(model.getId());
        dto.setTag_id(model.getTag_Id());
        dto.setBook_id(model.getBook_Id());
        return dto;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
