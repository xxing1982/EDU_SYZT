package com.syzton.sunread.dto.bookshelf;

import org.hibernate.validator.constraints.Length;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.bookshelf.BookInShelf;
import com.syzton.sunread.model.bookshelf.Bookshelf;

public class BookInShelfDTO {
    private Long id;

    @Length(max = BookInShelf.MAX_LENGTH_DESCRIPTION)
    private String description;

    @NotEmpty
    private boolean isMandatory;

    @NotEmpty
    private int readState;

    @NotEmpty
    private Bookshelf bookshelf_id;

    @NotEmpty
    private Book book_id;

    public BookInShelfDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getBookAttribute() {
        return isMandatory;
    }

    public void setBookAttribute(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public int getReadState() {
		return readState;
	}

    public void setReadState(int readState) {
		this.readState = readState;
	}

    public Book getBook() {
		return book_id;
	}
    public void setBook(Book book) {
		this.book_id = book;
	}

	  public Bookshelf getBookshelf() {
	  return bookshelf_id;
	}
	  public void setBookshelf(Bookshelf bookshelf) {
	  this.bookshelf_id = bookshelf;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
