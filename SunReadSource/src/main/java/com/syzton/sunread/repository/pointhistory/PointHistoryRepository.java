package com.syzton.sunread.repository.pointhistory;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.model.user.Student;

/**
 * @author chenty
 *
 */
public interface PointHistoryRepository extends JpaRepository<PointHistory,Long> {
	List<PointHistory> findByStudent(Student student);
	
	@Query("SELECT Distinct(b) FROM PointHistory b WHERE b.creationTime between (:startTime) AND (:endTime)")
	ArrayList<PointHistory> findBySemester(@Param("startTime")DateTime startTime, @Param("endTime")DateTime endTime);

}
