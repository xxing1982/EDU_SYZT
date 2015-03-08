package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
/**
 * Created by jerry on 3/8/15.
 */
public interface BookService {

    public Book add(BookDTO bookDTO);

}
