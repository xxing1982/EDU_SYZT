package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Category;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Created by jerry on 3/8/15.
 */
public interface BookService {

    public BookDTO add(BookDTO bookDTO);

    public Book findById(Long id) throws NotFoundException;

    public Book deleteById(Long id) throws NotFoundException;

    Page<Book> findAll(Pageable pageable) throws NotFoundException;

    Page<Book> findByCategories(Set<Long> categoryIds,Pageable pageable) throws NotFoundException;
}
