package com.syzton.sunread.repository.exam;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	//@Query("select exam from Exam exam wehre Exam.book = ?1 and ")
	List<Exam> findByStudentIdAndBookIdAndExamTypeAndCreationTimeAfter(Long studentId,Long bookId,ExamType type,DateTime date);
	
	List<Exam> findByStudentIdAndBookIdAndExamType(Long studentId,Long bookId,ExamType type);
	
	List<Exam> findByStudentIdAndExamTypeAndIsPass(Long id,ExamType examType,boolean isPass);
	
	List<Exam> findByStudentIdAndExamTypeAndIsPassAndCreationTimeBetween(Long id,ExamType examType,boolean isPass,DateTime from,DateTime to);
	
	List<Exam> findByStudentIdAndExamType(Long id,ExamType examType);
	
	List<Exam> findByStudentIdOrderByCreationTimeDesc(Long studentId);

}
