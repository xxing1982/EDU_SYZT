package com.syzton.sunread.repository.fish;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.fish.StudentFish;


/**
 * Created by jerry on 4/21/15.
 */
public interface StudentFishRepository extends JpaRepository<StudentFish,Long>{
    
    public StudentFish findByStudentId(long studentId);
}
