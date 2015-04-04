package com.syzton.sunread.controller.task;

import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jerry on 3/29/15.
 */
@Controller
@RequestMapping("/api")
public class TaskController {
    TaskService taskService ;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @RequestMapping(value = "/tasks",method = RequestMethod.POST)
//    @ResponseBody
//    public Task add(@RequestBody Task task){
//        return taskService.add(task);
//    }

    @RequestMapping(value = "/tasks/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id){
        taskService.deleteById(id);
    }
    @RequestMapping(value = "/tasks/{studentId}",method = RequestMethod.GET)
    @ResponseBody
    public Task add(@PathVariable("studentId") Long studentId){
        return taskService.findByStudentId(studentId);
    }
}
