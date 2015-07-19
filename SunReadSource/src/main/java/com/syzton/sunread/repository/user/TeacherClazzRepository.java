package com.syzton.sunread.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.TeacherClazz;

/**
 * Created by jerry on 3/16/15.
 */
@Repository
public interface TeacherClazzRepository extends JpaRepository<TeacherClazz,Long>{
    @Query("select c from Clazz c where c.id in(select tc.clazzId from TeacherClazz tc where tc.teacherId = :teacherId)")
    public List<Clazz> findByTeacherId(@Param("teacherId")long teacherId);

    public TeacherClazz findByTeacherIdAndClazzId(Long teacherId,Long clazzId);
	
}
