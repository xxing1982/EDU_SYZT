package com.syzton.sunread.repository.user;

import com.syzton.sunread.model.user.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by jerry on 3/22/15.
 */
public interface StudentRepository extends JpaRepository<Student,Long> {

    public Student findByUserId(String userId);

    List<Student> findByClazzId(long clazzId);

    Page<Student> findByClazzId(long clazzId,Pageable pageable);

    Page<Student> findByGradeId(long clazzId,Pageable pageable);

    Page<Student> findByCampusId(long campusId,Pageable pageable);

 }
