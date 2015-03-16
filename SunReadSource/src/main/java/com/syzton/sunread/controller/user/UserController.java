package com.syzton.sunread.controller.user;

import com.syzton.sunread.model.book.Category;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.service.book.CategoryService;
import com.syzton.sunread.service.user.UserService;
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
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private UserService userService;


    @Autowired
    public void setReviewService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public User add(@Valid @RequestBody User user) {
        return userService.add(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteById(@PathVariable("id") Long id) throws NotFoundException{

        userService.deleteById(id);

    }


    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    @ResponseBody
    public User findById(@PathVariable("id") Long id) throws NotFoundException{
        return userService.findById(id);
    }

}
