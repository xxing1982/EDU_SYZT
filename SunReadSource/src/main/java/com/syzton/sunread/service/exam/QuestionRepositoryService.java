package com.syzton.sunread.service.exam;

import java.util.List;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.repository.exam.QuestionRepository;

@Service
public class QuestionRepositoryService implements QuestionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(QuestionRepositoryService.class);

	private QuestionRepository<Question,Long> repository;



	@Autowired
	public QuestionRepositoryService(QuestionRepository<Question,Long> repository) {
		this.repository = repository;
	}

	@Transactional
	@Override
	public Question add(QuestionDTO added) {
		LOGGER.debug("Adding a new Answer entry with information: {}", added);

		//
		Question model = added.OTD();

		return repository.save(model);
	}

	@Transactional(rollbackFor = { QuestionNotFoundExcepiton.class })
	@Override
	public Question deleteById(Long id) throws QuestionNotFoundExcepiton {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		Question deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<Question> findAll(Pageable pageable) {
		Page<Question> questionPages = repository.findAll(pageable);

		return questionPages;
	}

	@Transactional(readOnly = true, rollbackFor = { QuestionNotFoundExcepiton.class })
	@Override
	public Question findById(Long id) throws QuestionNotFoundExcepiton {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		Question found = repository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new QuestionNotFoundExcepiton("No to-entry found with id: " + id);
		}

		return found;
	}

	@Transactional(rollbackFor = { QuestionNotFoundExcepiton.class })
	@Override
	public Question update(QuestionDTO updated) throws QuestionNotFoundExcepiton {
		LOGGER.debug("Updating contact with information: {}", updated);

		Question model = findById(updated.getId());
		LOGGER.debug("Found a to-do entry: {}", model);

		model.update(updated);

		return model;
	}

}
