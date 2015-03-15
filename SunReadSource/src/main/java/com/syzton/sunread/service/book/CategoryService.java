package com.syzton.sunread.service.book;

import com.syzton.sunread.dto.book.CategoryDTO;
import com.syzton.sunread.model.book.Category;
import javassist.NotFoundException;

/**
 * Created by jerry on 3/9/15.
 */
public interface CategoryService {

    public Category findById(Long id) throws NotFoundException;

    public Category add(Category category);

    Category deleteById(Long id);

}
