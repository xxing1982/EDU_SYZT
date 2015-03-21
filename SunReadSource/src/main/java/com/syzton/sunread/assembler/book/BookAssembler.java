package com.syzton.sunread.assembler.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.BookExtra;
import com.syzton.sunread.model.book.Category;
import com.syzton.sunread.repository.book.CategoryRepository;
import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jerry on 3/16/15.
 */
public class BookAssembler {

    public Book fromDTOtoEntireBook(final BookDTO bookDTO,final CategoryRepository categoryRepository){

        Set<Category> categorySet = new HashSet<>();

        for(Long caId: bookDTO.getCategories()){
            categorySet.add(categoryRepository.findOne(caId));
        }

        BookExtra extra = this.fromDTOToExtra(bookDTO);
        extra.setCategories(categorySet);


        Book book = this.fromDTOtoBookWithExtra(bookDTO);
        book.setExtra(extra);
        return book;
    }



    public Book fromDTOtoBookWithExtra(final BookDTO bookDTO){



        BookExtra extra = this.fromDTOToExtra(bookDTO);

        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setCoin(bookDTO.getCoin());
        book.setDescription(bookDTO.getDescription());
        book.setPoint(bookDTO.getPoint());
        book.setPublicationDate(new DateTime(bookDTO.getPublicationDate()));
        book.setPictureUrl(bookDTO.getPictureUrl());
        book.setExtra(extra);


        return book;
    }

    public BookExtra fromDTOToExtra(final BookDTO bookDTO){
        BookExtra extra = new BookExtra();
        extra.setLanguage(bookDTO.getLanguage());
        extra.setLevel(bookDTO.getLevel());
        extra.setLiterature(bookDTO.getLiterature());
        extra.setTestType(bookDTO.getTestType());
        return extra;
    }
}
