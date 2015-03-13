package com.syzton.sunread.dto.supplementbook;

import java.sql.Date;

/*
 * @Data 2015/03/13
 * @Author Morgan-Leon
 */
public class SupplementBookDTO {

	 private Long id;

	    private String isbn;

	    private String name;

	    private String description;

	    private Date publicationDate;

	    public Date getPublicationDate() {
	        return publicationDate;
	    }

	    public void setPublicationDate(Date publicationDate) {
	        this.publicationDate = publicationDate;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getIsbn() {
	        return isbn;
	    }

	    public void setIsbn(String isbn) {
	        this.isbn = isbn;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }
}
