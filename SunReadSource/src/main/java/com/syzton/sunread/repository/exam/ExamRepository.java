package com.syzton.sunread.repository.exam;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	//@Query("select exam from Exam exam wehre Exam.book = ?1 and ")
	List<Exam> findByStudentIdAndBookIdAndCreationTimeAfter(Long studentId,Long bookId,DateTime date);
	List<Exam> findByStudentIdAndBookId(Long studentId,Long bookId);

}
