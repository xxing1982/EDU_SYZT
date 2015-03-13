package com.syzton.sunread.repository.exam;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.syzton.sunread.model.exam.ObjectiveQuestion;

public interface ObjectiveQuestionRepository extends QuestionRepository<ObjectiveQuestion, Long> {
	
	@Query("select que from Question que order by RAND()")
	public List<ObjectiveQuestion> findTop5RandamQuestions(); 
	
	public long count();
}
