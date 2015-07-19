 package com.syzton.sunread.service.user;

 import com.syzton.sunread.exception.common.DuplicateException;
 import com.syzton.sunread.model.organization.Clazz;
 import com.syzton.sunread.model.user.*;
 import com.syzton.sunread.repository.user.*;
 import com.syzton.sunread.service.organization.ClazzService;
 import javassist.NotFoundException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import java.util.List;


 /**
  * Created by jerry on 3/16/15.
  */
 @Service
 public class TeacherClazzRepositoryService implements TeacherClazzService{

     private TeacherClazzRepository teacherClazzRepository;

     private ClazzService clazzService;

     private UserService userService;
     @Autowired
     public TeacherClazzRepositoryService(TeacherClazzRepository teacherClazzRepository, ClazzService clazzService, UserService userService) {
         this.teacherClazzRepository = teacherClazzRepository;
         this.clazzService = clazzService;
         this.userService = userService;
     }


     @Override
     public List<Clazz> findByTeacherId(long teacherId) {


         return teacherClazzRepository.findByTeacherId(teacherId);
     }

     @Override
     public void save(Long teacherId,Long clazzId) throws NotFoundException {
         getTeacher(teacherId);
         Clazz clazz = clazzService.findById(clazzId);
         if(clazz == null){
             throw new NotFoundException("clazz id = "+clazzId+" not found..");
         }

         TeacherClazz exits = teacherClazzRepository.findByTeacherIdAndClazzId(teacherId,clazzId);
         if(exits!=null){
             throw new DuplicateException("teacherId ="+teacherId+" clazzId = "+clazzId +" is already has relationship..");
         }

         TeacherClazz teacherClazz = new TeacherClazz();
         teacherClazz.setTeacherId(teacherId);
         teacherClazz.setClazzId(clazzId);
         teacherClazzRepository.save(teacherClazz);
     }

     @Override
     public Teacher updateCurrentClazz(Long teacherId, Long clazzId) throws NotFoundException{
         Teacher teacher = getTeacher(teacherId);

         Clazz clazz = clazzService.findById(clazzId);
         if(clazz == null){
             throw new NotFoundException("clazz id = "+clazzId+" not found..");
         }

         teacher.setCurrentClassId(clazzId);
         return teacher;
     }


     private Teacher getTeacher(Long teacherId) throws javassist.NotFoundException {
         Teacher teacher = userService.findByTeacherId(teacherId);
         if(teacher == null){
             throw new javassist.NotFoundException("teacher with id =" + teacherId +" not found...");
         }
         return teacher;
     }
 }
