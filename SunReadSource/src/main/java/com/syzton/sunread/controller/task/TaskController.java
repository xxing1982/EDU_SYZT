package com.syzton.sunread.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.service.task.TaskService;

/**
 * Created by jerry on 3/29/15.
 */
@Controller
@RequestMapping("/api")
public class TaskController extends BaseController{
    TaskService taskService ;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @RequestMapping(value = "/tasks/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id){
        taskService.deleteById(id);
    }
    @RequestMapping(value = "/tasks/students/{studentId}/semesters/{semesterId}",method = RequestMethod.GET)
    @ResponseBody
    public Task findByStudentId(@PathVariable("studentId") Long studentId,@PathVariable("semesterId") Long semesterId){
        Task task = taskService.findByStudentIdAndSemesterId(studentId,semesterId);
        if (task == null) task = new Task();
        return task;
    }

}
