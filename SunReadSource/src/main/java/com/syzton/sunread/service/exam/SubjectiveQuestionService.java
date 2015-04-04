package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SubjectiveQuestion;

public interface SubjectiveQuestionService {

	public Page<SubjectiveQuestion> findAll(Pageable pageable) throws NotFoundException;
	
	public SubjectiveQuestion findById(Long id) throws NotFoundException;
	
	public SubjectiveQuestion add(SubjectiveQuestion added);

	public SubjectiveQuestion deleteById(Long id) throws  NotFoundException;
	                                            
	public SubjectiveQuestion update(SubjectiveQuestion updated) throws NotFoundException;
}
