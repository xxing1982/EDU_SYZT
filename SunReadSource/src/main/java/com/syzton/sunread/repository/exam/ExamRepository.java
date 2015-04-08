package com.syzton.sunread.repository.exam;

import java.awt.print.Pageable;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.dto.exam.VerifyExamPassDTO;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	//@Query("select exam from Exam exam wehre Exam.book = ?1 and ")
	List<Exam> findByStudentIdAndBookIdAndCreationTimeAfter(Long studentId,Long bookId,DateTime date);
	
	List<Exam> findByStudentIdAndBookId(Long studentId,Long bookId);
	
	List<Exam> findByStudentIdAndExamTypeAndIsPass(Long id,ExamType examType,boolean isPass);

}
