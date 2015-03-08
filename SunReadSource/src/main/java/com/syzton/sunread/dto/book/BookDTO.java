package com.syzton.sunread.dto.book;

/**
 * Created by jerry on 3/8/15.
 */
public class BookDTO {

    private String isbn;

    private String name;

    private String description;

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
