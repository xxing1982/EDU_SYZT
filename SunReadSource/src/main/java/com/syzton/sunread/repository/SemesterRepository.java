package com.syzton.sunread.repository;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.semester.Semester;

public interface SemesterRepository extends JpaRepository<Semester,Long>{
	@Query("SELECT Distinct(s) FROM Semester s WHERE s.startTime>(:time) AND s.endTime<(:time)")
	Semester findByTime(@Param("time")DateTime time);
	@Query("SELECT Distinct(s) FROM Semester s WHERE s.startTime>(:startTime) AND s.endTime<(:endTime)")
	ArrayList<Semester> findByDuration(@Param("startTime")DateTime startTime,@Param("endTime")DateTime endTime);

}
