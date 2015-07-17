package com.syzton.sunread.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.model.user.Teacher;
import com.syzton.sunread.repository.task.TaskRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.repository.user.TeacherRepository;

/**
 * Created by jerry on 3/29/15.
 */
@Service
public class TaskRepositoryService implements TaskService{

    TaskRepository taskRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;
    @Autowired
    public TaskRepositoryService(TaskRepository taskRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.taskRepository = taskRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Task add(Task task) {
        Teacher teacher = teacherRepository.findOne(task.getTeacherId());
        if(teacher == null){
            throw new NotFoundException("teacher id = "+task.getTeacherId()+" not found...");
        }
        Student student = studentRepository.findOne(task.getStudentId());
        if(student == null){
            throw new NotFoundException("student id = "+task.getStudentId()+" not found...");
        }
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
         taskRepository.delete(id);
    }

    @Override
    public Task findByStudentIdAndSemesterId(Long studentId,Long semesterId) {
        Student student = studentRepository.findOne(studentId);
        if(student == null){
            throw new NotFoundException("student id = "+studentId+" not found...");
        }
        return taskRepository.findByStudentIdAndSemesterId(studentId,semesterId);
    }

//    @Override
//    public Page<Task> findByTeacherId(Long teacherId,Pageable pageable) {
//        Teacher teacher = teacherRepository.findOne(teacherId);
//        if(teacher == null){
//            throw new NotFoundException("teacher id = "+teacherId+" not found...");
//        }
//
//        return taskRepository.findByTeacherId(teacherId,pageable);
//    }
}
