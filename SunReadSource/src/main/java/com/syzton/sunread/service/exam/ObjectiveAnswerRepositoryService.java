package com.syzton.sunread.service.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.repository.exam.AnswerRepository;
import com.syzton.sunread.repository.exam.ObjectiveAnswerRepository;

@Service
public class ObjectiveAnswerRepositoryService  implements ObjectiveAnswerService{
	
	private ObjectiveAnswerRepository repository;
	
	@Autowired
	public ObjectiveAnswerRepositoryService(ObjectiveAnswerRepository repository) {
		this.repository = repository;
	}
	
	

}
