package com.syzton.sunread.repository.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.ObjectiveAnswer;

public interface ObjectiveAnswerRepository extends JpaRepository<ObjectiveAnswer, Long> {
	
}