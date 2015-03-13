package com.syzton.sunread.model.book;

import com.syzton.sunread.model.common.AbstractEntity;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry zhang
 */
@Entity
@Table(name="category")
public class Category extends AbstractEntity{

    public static final int MAX_LENGTH_NAME = 100;

    @Column(name="name",nullable = false,length = MAX_LENGTH_NAME)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @OrderColumn
    @Basic(fetch = FetchType.EAGER)
    private Set<Category> children = new HashSet<>() ;


//    @ManyToMany(cascade = CascadeType.REFRESH)
//    @Basic(fetch = FetchType.LAZY)
//    @JoinTable(name="book_category",
//            joinColumns=
//            @JoinColumn(name="category_id", referencedColumnName="id"),
//            inverseJoinColumns=
//            @JoinColumn(name="book_id", referencedColumnName="id")
//    )
//    private Set<Book> books = new HashSet<>();


    public Category() {

    }
//    public static Builder getBuilder(String name) {
//        return new Builder(name);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Book> getBooks() {
//        return books;
//    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

//    public void setBooks(Set<Book> books) {
//        this.books = books;
//    }

    //    public static class Builder {
//
//        private Category built;
//
//        public Builder(String name) {
//            built = new Category();
//            built.name = name;
//        }
//        public Builder children(Set<com.syzton.sunread.dto.book.Category> children){
//            Set<Category> categories = new HashSet<>();
//            for(com.syzton.sunread.dto.book.Category dto : children)
//            {
//
//            }
//            built.children =children;
//            return this;
//        }
//
//        public Builder books(Set<Book> books){
//            built.books =books;
//            return this;
//        }
//        public Category build() {
//            return built;
//        }
//
//
//    }
//    public com.syzton.sunread.dto.book.Category createBasicDTO() {
//        com.syzton.sunread.dto.book.Category dto = new com.syzton.sunread.dto.book.Category();
//        dto.setId(this.getId());
//        dto.setName(this.getName());
//
//        return dto;
//    }
//
//    public com.syzton.sunread.dto.book.Category createDTO(Set<Category> children){
//        com.syzton.sunread.dto.book.Category bookCategoryDTO = this.createBasicDTO();
//        Set<com.syzton.sunread.dto.book.Category> childrenDTO = new HashSet<>();
//        for(Category bookCategory: children){
//            childrenDTO.add(bookCategory.createBasicDTO());
//        }
//        bookCategoryDTO.setChildren(childrenDTO);
//        return bookCategoryDTO;
//    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
