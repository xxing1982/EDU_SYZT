package com.syzton.sunread.repository.exam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion.QuestionType;

public interface ObjectiveQuestionRepository extends JpaRepository<ObjectiveQuestion, Long>,JpaSpecificationExecutor<ObjectiveQuestion> {
	
	@Query("select que from Question que order by RAND()")
	public List<ObjectiveQuestion> findTop5RandamQuestions(); 
	
	public List<ObjectiveQuestion> findByBookIdAndObjectiveType(Long bookId,QuestionType type);
	
	public ObjectiveQuestion findByTopicAndBookId(String topic,Long bookId);
	 
}
