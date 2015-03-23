package com.syzton.sunread.repository.user;

import com.syzton.sunread.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jerry on 3/22/15.
 */
public interface StudentRepository extends JpaRepository<Student,Long> {

    public Student findByUserId(String userId);
}
