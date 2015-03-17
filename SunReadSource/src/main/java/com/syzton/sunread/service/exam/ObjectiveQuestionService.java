package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.model.exam.ObjectiveQuestion;

public interface ObjectiveQuestionService {
	
	public Page<ObjectiveQuestion> findAll(Pageable pageable) throws NotFoundException;
}
