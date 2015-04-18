package com.syzton.sunread.repository.exam;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveAnswer;

public interface SubjectiveAnswerRepository extends JpaRepository<SubjectiveAnswer, Long>{
	//@Query(value="select * from answer a where a.question_id=?1 and a.id <> ?2",nativeQuery=true)
	public Page<SubjectiveAnswer> findByQuestionAndCampusIdAndIdNot(Question question,Long campusId,Long id, Pageable pageable);
}
