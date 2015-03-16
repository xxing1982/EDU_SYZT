package com.syzton.sunread.assembler.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Category;
import com.syzton.sunread.repository.book.CategoryRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jerry on 3/16/15.
 */
public class BookAssembler {

    public Book fromDTO(final BookDTO bookDTO,final CategoryRepository categoryRepository){

        Set<Category> categorySet = new HashSet<>();

        for(Long caId: bookDTO.getCategories()){
            categorySet.add(categoryRepository.findOne(caId));
        }
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setCategories(categorySet);
        return book;
    }

}
