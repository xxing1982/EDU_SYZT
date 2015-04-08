package com.syzton.sunread.dto.bookshelf;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import com.syzton.sunread.model.bookshelf.BookInShelf;

public class BookInShelfDTO {
    private Long id;

    @Length(max = BookInShelf.MAX_LENGTH_DESCRIPTION)
    private String description;

    private String bookIsbn;
    
    private Long bookshelf;
    
    @NotNull
    private boolean isMandatory;

    @NotNull
    private boolean isVerified;
    
    private Long createTime;
    
    private Long modificationTime;
    
    private Long verifyTime;

    public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Long modificationTime) {
		this.modificationTime = modificationTime;
	}	

	public Long getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Long verifyTime) {
		this.verifyTime = verifyTime;
	}

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
//        this.description = description;
    }

    public boolean getBookAttribute() {
        return isMandatory;
    }

    public void setBookAttribute(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public boolean getReadState() {
		return isVerified;
	}

    public void setReadState(boolean isVerified) {
		this.isVerified = isVerified;
	}

    public String getBookIsbn() {
		return bookIsbn;
	}
    public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}

	  public Long getBookshelf() {
	  return bookshelf;
	}
	  public void setBookshelf(Long bookshelf) {
	  this.bookshelf = bookshelf;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
