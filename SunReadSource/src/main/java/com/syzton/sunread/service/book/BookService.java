package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by jerry on 3/8/15.
 */
public interface BookService {

    public Book add(Book book);

    public Book findById(Long id) throws NotFoundException;

    public Book deleteById(Long id) throws NotFoundException;

    Page<Book> findAll(Pageable pageable) throws NotFoundException;
}
