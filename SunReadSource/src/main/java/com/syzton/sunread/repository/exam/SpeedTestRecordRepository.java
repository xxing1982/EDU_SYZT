package com.syzton.sunread.repository.exam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.exam.SpeedTestRecord;

public interface SpeedTestRecordRepository extends JpaRepository<SpeedTestRecord,Long> {
	
	//select * from (select * from `test` order by `date` desc) `temp`  group by category_id order by `date` desc
	
	 @Query(value="select * from (select * from (select * from speed_test_record where school_id = :schoolId) group by user_id order by speed desc, score desc ) order by speed desc,score desc limit :num",nativeQuery=true)
	 List<SpeedTestRecord> getTopStudentBySchool(@Param("num") int num,@Param("schoolId") long schoolId);
	 
	 @Query(value="select * from (select * from speed_test_record group by user_id order by speed desc, score desc ) order by speed desc,score desc limit :num",nativeQuery=true)
	 List<SpeedTestRecord> getTopStudent(@Param("num") int num);
	 
	 @Query(value="select * from speed_test_record where user_id = :userId order by creation_time desc limit :num",nativeQuery=true)
	 List<SpeedTestRecord> getPersonalHistory(@Param("num") int num,@Param("userId") long userId);

}
