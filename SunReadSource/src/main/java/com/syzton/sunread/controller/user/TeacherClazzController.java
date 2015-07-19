package com.syzton.sunread.controller.user;

import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.service.organization.ClazzService;
import com.syzton.sunread.service.user.TeacherClazzService;
import com.syzton.sunread.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by jerry on 7/14/15.
 */
@Controller
@RequestMapping("/api")
public class TeacherClazzController {

    private TeacherClazzService teacherClazzService;

    private ClazzService clazzService;

    private UserService userService;

    @Autowired
    public TeacherClazzController(TeacherClazzService teacherClazzService, ClazzService clazzService, UserService userService) {
        this.teacherClazzService = teacherClazzService;
        this.clazzService = clazzService;
        this.userService = userService;
    }

    @RequestMapping(value = "/teachers/{teacherId}/clazzs", method = RequestMethod.GET)
    @ResponseBody
    public List<Clazz> findClazzsByTeacherId(@PathVariable("teacherId") Long teacherId) throws NotFoundException {
        checkTeacher(teacherId);
        List<Clazz> clazzs = teacherClazzService.findByTeacherId(teacherId);

        return clazzs;

    }

    private void checkTeacher(Long teacherId) throws NotFoundException{
        Teacher teacher = userService.findByTeacherId(teacherId);
        if(teacher == null){
            throw new NotFoundException("teacher with id =" + teacherId +" not found...");
        }
    }

    @RequestMapping(value = "/teachers/{teacherId}/clazzs/{clazzId}", method = RequestMethod.POST)
    @ResponseBody
    public void saveTeacherClazz(@PathVariable("teacherId") Long teacherId,@PathVariable("clazzId") Long clazzId) throws NotFoundException {
        teacherClazzService.save(teacherId,clazzId);

    }

}
