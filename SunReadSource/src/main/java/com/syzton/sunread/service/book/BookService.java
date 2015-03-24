package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.ConditionDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Category;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

/**
 * Created by jerry on 3/8/15.
 */
public interface BookService {

    public BookDTO add(BookDTO bookDTO);

    public Book findById(Long id);

    public Book deleteById(Long id);

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByCategories(Set<Long> categoryIds,Pageable pageable);

    Page<Book> quickSearch(String searchTerm,Pageable pageable);

    Page<Book> searchByCondition(ConditionDTO condition,Pageable pageable);
}
