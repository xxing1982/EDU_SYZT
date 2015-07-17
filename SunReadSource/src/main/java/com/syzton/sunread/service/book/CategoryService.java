package com.syzton.sunread.service.book;

import javassist.NotFoundException;

import com.syzton.sunread.model.book.Category;

/**
 * Created by jerry on 3/9/15.
 */
public interface CategoryService {

    public Category findById(Long id) throws NotFoundException;

    public Category add(Category category);

    Category deleteById(Long id);

}
