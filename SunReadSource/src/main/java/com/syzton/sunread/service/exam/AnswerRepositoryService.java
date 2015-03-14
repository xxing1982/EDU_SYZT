package com.syzton.sunread.service.exam;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.repository.exam.AnswerRepository;
import com.syzton.sunread.repository.exam.ObjectiveAnswerRepository;
import com.syzton.sunread.repository.exam.SubjectiveAnswerRepository;
import com.syzton.sunread.todo.dto.TodoDTO;
import com.syzton.sunread.todo.model.Todo;
@Service
public class AnswerRepositoryService implements AnswerService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnswerRepositoryService.class);

	private AnswerRepository<Answer,Long> repository;



	@Autowired
	public AnswerRepositoryService(AnswerRepository<Answer,Long> repository) {
		this.repository = repository;
	}

	@Transactional
	@Override
	public Answer add(AnswerDTO added) {
		LOGGER.debug("Adding a new Answer entry with information: {}", added);

		//
		Answer model = added.OTD();

		return repository.save(model);
	}

	@Transactional(rollbackFor = { AnswerNotFoundException.class })
	@Override
	public Answer deleteById(Long id) throws AnswerNotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		Answer deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Answer> findAll() {
		LOGGER.debug("Finding all to-do entries");
		return repository.findAll();
	}

	@Transactional(readOnly = true, rollbackFor = { AnswerNotFoundException.class })
	@Override
	public Answer findById(Long id) throws AnswerNotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		Answer found = repository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new AnswerNotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}

	@Transactional(rollbackFor = { AnswerNotFoundException.class })
	@Override
	public Answer update(AnswerDTO updated) throws AnswerNotFoundException {
		LOGGER.debug("Updating contact with information: {}", updated);

		Answer model = findById(updated.getId());
		LOGGER.debug("Found a to-do entry: {}", model);

		model.update(updated);

		return model;
	}

}
