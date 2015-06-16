package com.syzton.sunread.repository;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.semester.Semester;

public interface SemesterRepository extends JpaRepository<Semester,Long>{
	@Query("SELECT Distinct(s) FROM Semester s WHERE s.startTime>(:time) AND s.endTime<(:time) AND s.campus=(:campusId)")
	Semester findByTimeAndCampusId(@Param("time")DateTime time,@Param("campusId")Long campusId);
	@Query("SELECT Distinct(s) FROM Semester s WHERE s.startTime>(:startTime) AND s.endTime<(:endTime) ORDER BY s.startTime DESC AND s.campus=(:campusId)")
	ArrayList<Semester> findByDuration(@Param("startTime")DateTime startTime,@Param("endTime")DateTime endTime,@Param("campusId")Long campusId);
	
	Page<Semester> findByCampus(Campus campus, Pageable pageable);
}
