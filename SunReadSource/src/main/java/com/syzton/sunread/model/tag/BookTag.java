package com.syzton.sunread.model.tag;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.tag.BookTagDTO;
import com.syzton.sunread.model.book.Book;

import javax.persistence.*;

/**
 * @author chenty
 *
 */
@Entity
@Table(name="booktag")
public class BookTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
	private Book book;
    
    @Column(name = "create_time", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createTime;

	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @Basic(fetch = FetchType.LAZY)
    @JoinColumn(name="tag_id")
	private Tag tag;
	

    public BookTag() {

    }

    public static Builder getBuilder(Tag tag, Book book) {
    	
        return new Builder(tag, book);
    }

    public Long getId() {
        return id;   
    }
    
    public Tag getTag() {
		return tag;
	}
    
    public Book getBook() {
    	return book;
    }

	public void update() {
    }

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        createTime = now;
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
        dto.setTag_id(model.getTag().getId());
        dto.setBook_id(model.getBook().getId());
        return dto;
    }
}
