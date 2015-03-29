package com.syzton.sunread.controller.user;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.model.book.Category;
import com.syzton.sunread.model.user.Parent;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.service.book.CategoryService;
import com.syzton.sunread.service.user.UserService;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by jerry on 3/9/15.
 */
@Controller
@RequestMapping(value = "/api")
public class UserController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private UserService userService;


    @Autowired
    public void setReviewService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public User add(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteById(@PathVariable("id") Long id){

        userService.deleteById(id);

    }

    
    @RequestMapping(value = "/tokens/{token}",method = RequestMethod.POST)
    public void verifyToken(@PathVariable("token") String token) {
//        verificationTokenService.verify(token);
//        return Response.ok().build();
    }



    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    @ResponseBody
    public User findById(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    @ResponseBody
    public Student add(@Valid @RequestBody Student student) {
        return userService.addStudent(student);
    }


    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteByStudentId(@PathVariable("id") Long id){

        userService.deleteByStudentId(id);

    }

    @RequestMapping(value = "/students/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Student findByStudentId(@PathVariable("id") Long id){
        return userService.findByStudentId(id);
    }
    /*student add parent*/
    //TODO Student role
    @RequestMapping(value = "/students/{id}/parents", method = RequestMethod.POST)
    @ResponseBody
    public Parent add(@Valid @RequestBody Parent parent,@PathVariable("id") Long id) {
        return userService.addParent(parent,id);
    }

    /*parent add student*/
    //TODO Parent role
    @RequestMapping(value = "/parents/{id}/students/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public Parent addChildren(@PathVariable("id") Long id,@PathVariable("userId") String userId) {

        return userService.addChildren(id, userId);
    }



}
