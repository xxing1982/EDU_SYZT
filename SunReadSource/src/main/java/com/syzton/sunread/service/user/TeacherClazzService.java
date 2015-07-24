package com.syzton.sunread.service.user;

import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.Teacher;

import javassist.NotFoundException;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by jerry on 7/16/15.
 */
public interface TeacherClazzService {

    List<Clazz> findByTeacherId(long teacherId);

    void save(Long teacherId,Long clazzId) throws NotFoundException;

    Teacher updateCurrentClazz(Long teacherId,Long clazzId) throws NotFoundException;

	Map<Integer, String> batchSaveOrUpdateTeacherFromExcel(Sheet sheet);



}
