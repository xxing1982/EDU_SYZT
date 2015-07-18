package com.syzton.sunread.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.TeacherClazz;

/**
 * Created by jerry on 3/16/15.
 */
@Repository
public interface TeacherClazzRepository extends JpaRepository<TeacherClazz,Long>{

    public List<Clazz> findByTeacherId(long teacherId);
	
}
