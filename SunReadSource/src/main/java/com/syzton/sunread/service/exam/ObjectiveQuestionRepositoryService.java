package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.repository.exam.ObjectiveQuestionRepository;
import com.syzton.sunread.service.book.BookRepositoryService;

@Service
public class ObjectiveQuestionRepositoryService implements
		ObjectiveQuestionService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ObjectiveQuestionRepositoryService.class);
	private ObjectiveQuestionRepository repository;

	@Autowired
	public ObjectiveQuestionRepositoryService(
			ObjectiveQuestionRepository repository) {
		this.repository = repository;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<ObjectiveQuestion> findAll(Pageable pageable)
			throws NotFoundException {

		Page<ObjectiveQuestion> objectiveQsPages = repository.findAll(pageable);

		return objectiveQsPages;

	}
}
