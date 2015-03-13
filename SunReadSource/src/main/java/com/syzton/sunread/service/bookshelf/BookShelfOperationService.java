package com.syzton.sunread.service.bookshelf;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.bookshelf.BookShelfOperationDTO;
import com.syzton.sunread.model.bookshelf.BookShelfOperation;

/*
 * @Date 2015/03/13
 * @Author Morgan-Leon
 */
public interface BookShelfOperationService {

    public BookShelfOperation add(BookShelfOperationDTO dto);

    public BookShelfOperation deleteById(Long id) throws NotFoundException;

    public BookShelfOperation update(BookShelfOperationDTO dto) throws NotFoundException;;

    public BookShelfOperation findById(Long id) throws NotFoundException;

    public Page<BookShelfOperation> findAll(Pageable pageable) throws NotFoundException;
}
