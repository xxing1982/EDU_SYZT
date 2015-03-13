package com.syzton.sunread.repository.exam;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

}
