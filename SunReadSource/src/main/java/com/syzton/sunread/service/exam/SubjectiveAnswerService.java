package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;

public interface SubjectiveAnswerService{

	public Page<SubjectiveAnswer> findAll(Pageable pageable)
			throws NotFoundException;
	
	public Page<SubjectiveAnswer> findAllByQuestionId(Pageable pageable,Long questionId)
			throws NotFoundException;
	
	public Page<SubjectiveAnswer> findOtherPersonAnswer(Long questionId,Long userId,Pageable pageable)
			throws NotFoundException;
	
	public SubjectiveAnswer add(SubjectiveAnswer added);

    public SubjectiveAnswer deleteById(Long id) throws NotFoundException;

    public SubjectiveAnswer findById(Long id) throws NotFoundException;
}
