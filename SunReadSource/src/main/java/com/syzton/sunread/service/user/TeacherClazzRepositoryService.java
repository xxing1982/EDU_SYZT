 package com.syzton.sunread.service.user;

 import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.security.Role;
import com.syzton.sunread.model.user.*;
import com.syzton.sunread.model.user.User.GenderType;
import com.syzton.sunread.repository.organization.CampusRepository;
import com.syzton.sunread.repository.user.*;
import com.syzton.sunread.service.organization.CampusRepositoryService;
import com.syzton.sunread.service.organization.ClazzService;
import com.syzton.sunread.util.ExcelUtil;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


 /**
  * Created by jerry on 3/16/15.
  */
 @Service
 public class TeacherClazzRepositoryService implements TeacherClazzService{

     private TeacherClazzRepository teacherClazzRepository;

     private ClazzService clazzService;

     private UserService userService;
     
     private CampusRepository campusRepo;
     @Autowired
     public TeacherClazzRepositoryService(TeacherClazzRepository teacherClazzRepository, ClazzService clazzService, UserService userService,CampusRepository campusRepo) {
         this.teacherClazzRepository = teacherClazzRepository;
         this.clazzService = clazzService;
         this.userService = userService;
         this.campusRepo = campusRepo;
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
         userService.addTeacher(teacher);
         return teacher;
     }


     private Teacher getTeacher(Long teacherId) throws javassist.NotFoundException {
         Teacher teacher = userService.findByTeacherId(teacherId);
         if(teacher == null){
             throw new javassist.NotFoundException("teacher with id =" + teacherId +" not found...");
         }
         return teacher;
     }
     
     @Override
 	public Map<Integer,String> batchSaveOrUpdateTeacherFromExcel(Sheet sheet) {
 		Map<Integer,String> failMap = new HashMap<Integer,String>();
 		
 		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {  
 			Row row = sheet.getRow(i);  
 			String userId = ExcelUtil.getStringFromExcelCell(row.getCell(0));
 			if("".equals(userId)){
 				break;
 			}
 			User user = userService.findByUserId(userId);
 			if(user == null){
 				failMap.put(i+1, "教师不存在:"+userId);
 				continue;
 			}
 			
 			Teacher teacher = userService.findByTeacherId(user.getId());
 			if(teacher == null){
 				failMap.put(i+1, "教师不存在:"+userId);
 				continue;
 			}
 			String campusName = ExcelUtil.getStringFromExcelCell(row.getCell(1));
 			String clazzName = ExcelUtil.getStringFromExcelCell(row.getCell(2));
 			Campus campus = campusRepo.findByName(campusName);
 			Clazz clazz = null;
 			try {
				clazz = clazzService.findByClazzNameAndCampus(clazzName, campus);
			} catch (NotFoundException e) {
				failMap.put(i+1, "班级不存在:"+clazzName);
 				continue;
			}
 			TeacherClazz tc = new TeacherClazz();
 			tc.setClazzId(clazz.getId());
 			tc.setTeacherId(teacher.getId());
 			teacherClazzRepository.save(tc);
 		}  
 		return failMap;
 	}
 }
