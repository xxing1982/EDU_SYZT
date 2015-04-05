package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
import com.syzton.sunread.repository.exam.ObjectiveAnswerRepository;

@Service
public class ObjectiveAnswerRepositoryService  implements ObjectiveAnswerService{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ObjectiveAnswerRepositoryService.class);
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
	
	
	@Transactional
	@Override
	public ObjectiveAnswer add(ObjectiveAnswer added) {
		LOGGER.debug("Adding a new Answer entry with information: {}", added);
		return repository.save(added);
	}

	@Transactional(rollbackFor = { AnswerNotFoundException.class })
	@Override
	public ObjectiveAnswer deleteById(Long id) throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		ObjectiveAnswer deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;
	}

	 
	@Transactional(readOnly = true, rollbackFor = { AnswerNotFoundException.class })
	@Override
	public ObjectiveAnswer findById(Long id) throws NotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		ObjectiveAnswer found = repository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}
	

}
