package com.syzton.sunread.service.book;

import java.util.List;
import java.util.Map;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.model.book.Book;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by jerry on 3/8/15.
 */
public interface BookService {

    public BookDTO add(BookDTO bookDTO);

    public BookDTO update(BookDTO bookDTO,Long bookId);

    public Book findById(Long id);

    public Book deleteById(Long id);

    Page<Book> findAll(Pageable pageable);

    Page<Book> quickSearch(String searchTerm,Pageable pageable);

    Page<Book> searchByCondition(BookExtraDTO condition,Pageable pageable);
    
    public Map<Integer,String> batchSaveOrUpdateBookFromExcel(Sheet sheet);

    Page<Book> searchByTags(long lesson,long subject,long grade,long chapter,long theme,Pageable pageable);
}
