package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.repository.exam.SubjectiveQuestionRepository;

@Service
public class SubjectiveQuestionRepositoryService implements SubjectiveQuestionService{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SubjectiveQuestionRepositoryService.class);
	private SubjectiveQuestionRepository repository;

	@Autowired
	public SubjectiveQuestionRepositoryService(
			SubjectiveQuestionRepository repository) {
		this.repository = repository;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<SubjectiveQuestion> findAll(Pageable pageable)
			throws NotFoundException {

		Page<SubjectiveQuestion> subjectiveQsPages = repository.findAll(pageable);

		return subjectiveQsPages;

	}
}
