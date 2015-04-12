package com.syzton.sunread.repository.exam;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.CapacityExamHistory;

public interface CapacityExamHistoryRepository extends JpaRepository<CapacityExamHistory, Long>{
	
	List<CapacityExamHistory> findByStudentIdAndIsPassAndCreationTimeBetween(Long studentId,boolean isPass,DateTime from,DateTime to);
	
	List<CapacityExamHistory> findByStudentIdAndCreationTimeBetween(Long studentId,DateTime from,DateTime to);
}
