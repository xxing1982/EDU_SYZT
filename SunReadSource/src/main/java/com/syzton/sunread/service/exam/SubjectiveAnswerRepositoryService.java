package com.syzton.sunread.service.exam;

import java.util.List;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.repository.exam.AnswerRepository;
import com.syzton.sunread.repository.exam.SubjectiveAnswerRepository;

@Service
public class SubjectiveAnswerRepositoryService implements SubjectiveAnswerService {
	
	private SubjectiveAnswerRepository repository;
	@Autowired
	public SubjectiveAnswerRepositoryService(SubjectiveAnswerRepository repository) {
		
		this.repository = repository;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<SubjectiveAnswer> findAll(Pageable pageable)
			throws NotFoundException {

		Page<SubjectiveAnswer> subjectiveAnswerPages = repository.findAll(pageable);

		return subjectiveAnswerPages;

	}

	@Override
	public Page<SubjectiveAnswer> findOtherPersonAnswer(Long questionId,
			Pageable pageable) throws NotFoundException {
		Page<SubjectiveAnswer> subjectiveAnswerPages = repository.findAll(pageable);
		return null;
	}
}
