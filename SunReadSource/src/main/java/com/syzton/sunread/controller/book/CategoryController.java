package com.syzton.sunread.controller.book;

import com.syzton.sunread.dto.book.CategoryDTO;
import com.syzton.sunread.model.book.Category;
import com.syzton.sunread.service.book.CategoryService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by jerry on 3/9/15.
 */
@Controller
@RequestMapping(value = "/api")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    public void setReviewService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    @ResponseBody
    public Category add(@Valid @RequestBody Category dto) {

        dto = categoryService.add(dto);
        return dto;
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Category deleteById(@PathVariable("id") Long id) {

        Category category = categoryService.deleteById(id);
        for(Category child :category.getChildren()){
            categoryService.deleteById(child.getId());
        }
        return category;
    }


    @RequestMapping(value = "/categories/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Category findById(@PathVariable("id") Long id) throws NotFoundException{
        Category category = categoryService.findById(id);
        return category;
    }

}
