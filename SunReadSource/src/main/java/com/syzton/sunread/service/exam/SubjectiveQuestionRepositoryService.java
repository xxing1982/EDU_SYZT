package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.exam.Question;
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
	
	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public SubjectiveQuestion findById(Long id) throws NotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		SubjectiveQuestion found = repository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}
	
	@Transactional
	@Override
	public SubjectiveQuestion add(SubjectiveQuestion added) {
		LOGGER.debug("Adding a new Question entry with information: {}", added);
		return repository.save(added);
	}

	@Transactional(rollbackFor = { QuestionNotFoundExcepiton.class })
	@Override
	public SubjectiveQuestion deleteById(Long id) throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		SubjectiveQuestion deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;
	}

	@Override
	public SubjectiveQuestion update(SubjectiveQuestion updated)
			throws NotFoundException {
		LOGGER.debug("Updating contact with information: {}", updated);

		SubjectiveQuestion model = findById(updated.getId());
		LOGGER.debug("Found a to-do entry: {}", model);

		model.setTopic(updated.getTopic());
		model.setBook(updated.getBook());
		model.setQuestionType(updated.getQuestionType());

		return model;
	}
}
