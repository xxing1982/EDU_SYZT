package com.syzton.sunread.repository.exam;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;

public interface SubjectiveQuestionRepository extends JpaRepository<SubjectiveQuestion, Long>{
	
	public List<SubjectiveQuestion> findByQuestionTypeAndBookId(SubjectiveQuestionType questionType,Long bookId );
	
}
