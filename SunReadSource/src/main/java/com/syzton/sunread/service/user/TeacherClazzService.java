package com.syzton.sunread.service.user;

import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.TeacherClazz;
import javassist.NotFoundException;

import java.util.List;

/**
 * Created by jerry on 7/16/15.
 */
public interface TeacherClazzService {

    List<Clazz> findByTeacherId(long teacherId);

    void save(Long teacherId,Long clazzId) throws NotFoundException;



}
