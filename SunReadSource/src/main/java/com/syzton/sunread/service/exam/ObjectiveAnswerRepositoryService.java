package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.repository.exam.ObjectiveAnswerRepository;

@Service
public class ObjectiveAnswerRepositoryService  implements ObjectiveAnswerService{
	
	private ObjectiveAnswerRepository repository;
	
	@Autowired
	public ObjectiveAnswerRepositoryService(ObjectiveAnswerRepository repository) {
		this.repository = repository;
	}
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<ObjectiveAnswer> findAll(Pageable pageable) throws NotFoundException {

		Page<ObjectiveAnswer> objectiveAnswerPages = repository.findAll(pageable);

		return objectiveAnswerPages;

	}
	

}
