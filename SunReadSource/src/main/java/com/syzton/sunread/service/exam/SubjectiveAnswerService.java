package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.model.exam.SubjectiveAnswer;

public interface SubjectiveAnswerService{

	public Page<SubjectiveAnswer> findAll(Pageable pageable)
			throws NotFoundException;
	
	public Page<SubjectiveAnswer> findOtherPersonAnswer(Long questionId,Pageable pageable)
			throws NotFoundException;
}
