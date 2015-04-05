package com.syzton.sunread.repository.exam;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.user.Student;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	//@Query("select exam from Exam exam wehre Exam.book = ?1 and ")
	List<Exam> findByStudentIdAndBookIdAndCreationTimeAfter(Long studentId,Long bookId,Date date);
	List<Exam> findByStudentIdAndBookId(Long studentId,Long bookId);

}
