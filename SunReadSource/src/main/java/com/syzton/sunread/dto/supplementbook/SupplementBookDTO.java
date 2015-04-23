package com.syzton.sunread.dto.supplementbook;

import java.util.Date;

import org.joda.time.DateTime;

/*
 * @Data 2015/03/13
 * @Author Morgan-Leon
 */
public class SupplementBookDTO {

		private Long id;
		
		private String author;
		
		private String publisher;
		
	    private String isbn;

	    private String name;
	    
	    private int language;

	    private String description;

	    private Date publicationDate;

	    public Date getPublicationDate() {
	        return publicationDate;
	    }

	    public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getPublisher() {
			return publisher;
		}

		public void setPublisher(String publisher) {
			this.publisher = publisher;
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

		/**
		 * @return the language
		 */
		public int getLanguage() {
			return language;
		}

		/**
		 * @param language the language to set
		 */
		public void setLanguage(int language) {
			this.language = language;
		}
}
