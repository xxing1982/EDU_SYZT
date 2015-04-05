package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveAnswer;


public interface ObjectiveAnswerService {
	
	public Page<ObjectiveAnswer> findAll(Pageable pageable) throws NotFoundException;
	
	public ObjectiveAnswer add(ObjectiveAnswer added);

    public ObjectiveAnswer deleteById(Long id) throws NotFoundException;
	 
    public ObjectiveAnswer findById(Long id) throws NotFoundException;
	
}
