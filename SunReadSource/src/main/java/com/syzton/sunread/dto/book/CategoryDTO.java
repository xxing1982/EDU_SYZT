package com.syzton.sunread.dto.book;

import com.syzton.sunread.dto.common.AbstractDTO;
import com.syzton.sunread.model.book.Book;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry zhang
 */
public class CategoryDTO extends AbstractDTO{

    private String name;

    private Set<CategoryDTO> children = new HashSet<>() ;

    private Set<Book> books = new HashSet<>();


    public CategoryDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryDTO> children) {
        this.children = children;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
