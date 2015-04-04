package com.syzton.sunread.repository.exam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.CapacityQuestion.CapacityQuestionType;

public interface CapacityQuestionRepository extends JpaRepository<CapacityQuestion, Long> {
	
	public List<CapacityQuestion> findByQuestionTypeAndLevel(CapacityQuestionType questionType,int level);
}

