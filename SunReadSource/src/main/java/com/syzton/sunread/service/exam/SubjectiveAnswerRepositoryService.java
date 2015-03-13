package com.syzton.sunread.service.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.repository.exam.AnswerRepository;
import com.syzton.sunread.repository.exam.SubjectiveAnswerRepository;

@Service
public class SubjectiveAnswerRepositoryService implements SubjectiveAnswerService {
	
	private SubjectiveAnswerRepository repository;
	@Autowired
	public SubjectiveAnswerRepositoryService(SubjectiveAnswerRepository repository) {
		
		this.repository = repository;
	}
}
