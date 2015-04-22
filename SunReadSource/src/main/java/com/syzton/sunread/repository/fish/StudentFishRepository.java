package com.syzton.sunread.repository.fish;

import com.syzton.sunread.model.fish.StudentFish;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by jerry on 4/21/15.
 */
public interface StudentFishRepository extends JpaRepository<StudentFish,Long>{
    
    public StudentFish findByStudentId(long studentId);
}
