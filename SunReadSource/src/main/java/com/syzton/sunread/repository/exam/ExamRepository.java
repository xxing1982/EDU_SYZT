package com.syzton.sunread.repository.exam;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	
	List<Exam> findByStudentIdAndBookIdAndExamTypeAndCreationTimeAfter(Long studentId,Long bookId,ExamType type,DateTime date);
	
	List<Exam> findByStudentIdAndBookIdAndExamTypeOrderByCreationTimeDesc(Long studentId,Long bookId,ExamType type);
	
	List<Exam> findByStudentIdAndBookIdAndExamTypeAndIsPass(Long studentId,Long bookId,ExamType type,boolean isPass);
	
	List<Exam> findByStudentIdAndExamTypeAndIsPassOrderByCreationTimeDesc(Long id,ExamType examType,boolean isPass);
	
	List<Exam> findByStudentIdAndExamTypeAndIsPassAndCreationTimeBetweenOrderByCreationTimeDesc(Long id,ExamType examType,boolean isPass,DateTime from,DateTime to);
	
	List<Exam> findByStudentIdAndExamTypeOrderByCreationTimeDesc(Long id,ExamType examType);
	
	List<Exam> findByStudentIdAndFirstPassAndExamTypeAndCreationTimeBetween(Long studentId,boolean firstPass,ExamType type,DateTime from,DateTime to);
	
	List<Exam> findByStudentIdAndSecondPassAndExamTypeAndCreationTimeBetween(Long studentId,boolean firstPass,ExamType type,DateTime from,DateTime to);
	
	List<Exam> findByStudentIdOrderByCreationTimeDesc(Long studentId);

}
