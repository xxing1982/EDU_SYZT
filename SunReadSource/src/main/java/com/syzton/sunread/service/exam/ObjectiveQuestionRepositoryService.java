package com.syzton.sunread.service.exam;

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
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.ObjectiveQuestion.QuestionType;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.exam.CapacityQuestionRepository;
import com.syzton.sunread.repository.exam.ObjectiveQuestionRepository;
import com.syzton.sunread.repository.exam.OptionRepository;
import com.syzton.sunread.service.book.BookRepositoryService;

@Service
public class ObjectiveQuestionRepositoryService implements
		ObjectiveQuestionService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ObjectiveQuestionRepositoryService.class);
	private ObjectiveQuestionRepository repository;
	
	private OptionRepository optionRepository;
	
	private CapacityQuestionRepository capacityRepository;
	
	private BookRepository bookRepository;

	@Autowired
	public ObjectiveQuestionRepositoryService(
			ObjectiveQuestionRepository repository,OptionRepository optionRepository,CapacityQuestionRepository capacityRepository,BookRepository bookRepository) {
		this.repository = repository;
		this.optionRepository = optionRepository;
		this.capacityRepository = capacityRepository;
		this.bookRepository = bookRepository;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<ObjectiveQuestion> findAll(Pageable pageable)
			throws NotFoundException {

		Page<ObjectiveQuestion> objectiveQsPages = repository.findAll(pageable);

		return objectiveQsPages;

	}

	@Transactional
	@Override
	public ObjectiveQuestion add(ObjectiveQuestion added) {
		LOGGER.debug("Adding a new Objective question entry with information: {}", added);
		Long bookId = added.getBookId();
		Book book = null;
		if(added.getObjectiveType().equals(QuestionType.VERIFY)){
			book = bookRepository.findOne(bookId);
			book.getExtra().setHasVerifyTest(true);
			bookRepository.save(book);
			
		}else if(added.getObjectiveType().equals(QuestionType.WORD)){
			book = bookRepository.findOne(bookId);
			book.getExtra().setHasWordTest(true);
			bookRepository.save(book);
		}
		return repository.save(added);
	}

	@Override
	public ObjectiveQuestion deleteById(Long id)
			throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		ObjectiveQuestion deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;
	}
	
	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public ObjectiveQuestion findById(Long id) throws NotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		ObjectiveQuestion found = repository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public ObjectiveQuestion update(ObjectiveQuestion updated)
			throws NotFoundException {
		LOGGER.debug("Updating contact with information: {}", updated);

		ObjectiveQuestion model = findById(updated.getId());
		LOGGER.debug("Found a to-do entry: {}", model);
		model.setCorrectAnswer(updated.getCorrectAnswer());
		model.setObjectiveType(updated.getObjectiveType());
		model.setOptions(updated.getOptions());
		model.setBookId(updated.getBookId());
		model.setTopic(updated.getTopic());
		
		return model;
	}
	
	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public Page<Option> findAllOption(Pageable pageable)
			throws NotFoundException {
		Page<Option> optionPages = optionRepository.findAll(pageable);
		return optionPages;
	}
	
	@Transactional
	@Override
	public Option addOption(Option added) {
		LOGGER.debug("Adding a new Objective question entry with information: {}", added);
		return optionRepository.save(added);
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Option deleteOptionById(Long id) throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		Option deleted = findOptionById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		optionRepository.delete(deleted);
		return deleted;
	}

	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public Option findOptionById(Long id) throws NotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		Option found = optionRepository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Option updateOption(Option updated) throws NotFoundException {
		LOGGER.debug("Updating contact with information: {}", updated);

		Option model = findOptionById(updated.getId());
		LOGGER.debug("Found a to-do entry: {}", model);
		model.setContent(updated.getContent());
		model.setTag(updated.getTag());
		return model;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public ObjectiveQuestion setCorrectOption(Long questionId,
			Long optionId) throws NotFoundException{
		ObjectiveQuestion question = findById(questionId);
		Option option = findOptionById(optionId);
		question.setCorrectAnswer(option);
		
		return question;
	}

	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public Page<CapacityQuestion> findAllCapacityQuestion(Pageable pageable)
			throws NotFoundException {
		Page<CapacityQuestion> capacityPages = capacityRepository.findAll(pageable);
		return capacityPages;
 
	}
	
	@Transactional
	@Override
	public CapacityQuestion addCapacityQuestion(CapacityQuestion added) {
		LOGGER.debug("Adding a new Objective question entry with information: {}", added);
		return capacityRepository.save(added);
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public CapacityQuestion deleteCapacityQuestionById(Long id)
			throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		CapacityQuestion deleted = findCapacityQuestionById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		capacityRepository.delete(deleted);
		return deleted;
	}
	
	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public CapacityQuestion findCapacityQuestionById(Long id)
			throws NotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		CapacityQuestion found = capacityRepository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public CapacityQuestion updateCapacityQuestion(CapacityQuestion updated)
			throws NotFoundException {
		LOGGER.debug("Updating contact with information: {}", updated);

		CapacityQuestion model = findCapacityQuestionById(updated.getId());
		LOGGER.debug("Found a to-do entry: {}", model);
		model.setLevel(updated.getLevel());
		model.setQuestionType(updated.getQuestionType());
		model.setCorrectAnswer(updated.getCorrectAnswer());
		model.setOptions(updated.getOptions());
		model.setObjectiveType(updated.getObjectiveType());
		model.setTopic(updated.getTopic());
		return model;
	}
	

 
}
