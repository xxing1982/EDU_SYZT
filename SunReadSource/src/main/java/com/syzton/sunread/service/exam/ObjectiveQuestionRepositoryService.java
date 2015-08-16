package com.syzton.sunread.service.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion.QuestionType;
import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.SpeedQuestion;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.exam.CapacityQuestionRepository;
import com.syzton.sunread.repository.exam.ObjectiveQuestionRepository;
import com.syzton.sunread.repository.exam.OptionRepository;
import com.syzton.sunread.repository.exam.SpeedQuestionRepository;
import com.syzton.sunread.util.ExcelUtil;

@Service
public class ObjectiveQuestionRepositoryService implements
		ObjectiveQuestionService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ObjectiveQuestionRepositoryService.class);
	private ObjectiveQuestionRepository repository;
	
	private OptionRepository optionRepository;
	
	private CapacityQuestionRepository capacityRepository;
	
	private SpeedQuestionRepository speedRepository;
	
	private BookRepository bookRepository;

	@Autowired
	public ObjectiveQuestionRepositoryService(
			ObjectiveQuestionRepository repository,OptionRepository optionRepository,
			CapacityQuestionRepository capacityRepository,SpeedQuestionRepository speedRepository,
			BookRepository bookRepository) {
		this.repository = repository;
		this.optionRepository = optionRepository;
		this.capacityRepository = capacityRepository;
		this.speedRepository = speedRepository;
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
	
	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public Page<SpeedQuestion> findAllSpeedQuestion(Pageable pageable)
			throws NotFoundException {
		Page<SpeedQuestion> speedPages = speedRepository.findAll(pageable);
		return speedPages;
 
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
	
	@Transactional
	@Override
	public SpeedQuestion addSpeedQuestion(SpeedQuestion added) {
		LOGGER.debug("Adding a new Objective question entry with information: {}", added);
		return speedRepository.save(added);
	}
	
	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public SpeedQuestion findSpeedQuestionById(Long id)
			throws NotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		SpeedQuestion found = speedRepository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public SpeedQuestion deleteSpeedQuestionById(Long id)
			throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		SpeedQuestion deleted = speedRepository.findOne(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		speedRepository.delete(deleted);
		return deleted;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public SpeedQuestion updateSpeedQuestion(SpeedQuestion updated)
			throws NotFoundException {
		LOGGER.debug("Updating contact with information: {}", updated);

		SpeedQuestion model = speedRepository.findOne(updated.getId());
		LOGGER.debug("Found a to-do entry: {}", model);
		model.setCorrectAnswer(updated.getCorrectAnswer());
		model.setOptions(updated.getOptions());
		model.setObjectiveType(updated.getObjectiveType());
		model.setTopic(updated.getTopic());
		return model;
	}
	
	@Transactional
	@Override
	public Map<Integer,String> batchSaveOrUpdateObjectiveQuestionFromExcel(Sheet sheet){
		ObjectiveQuestion currentQuestion = null;
		Map<Integer,String> failMap = new HashMap<Integer,String>();
		int total = sheet.getPhysicalNumberOfRows();
		for(int i=sheet.getFirstRowNum()+1;i<total-1;i++){
			Row row = sheet.getRow(i);
			if(row.getCell(0) == null){
				break;
			}
			String type = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			type=type.trim();
			if("词汇测试".equals(type)||"验证测试".equals(type)){
				currentQuestion = updateOrSaveQuestionFromRow(failMap,row);
			}else if("选项".equals(type)){
				updateOrSaveOptionFromRow(failMap,row,currentQuestion);
			}else if("速度测试".equals(type)){
				currentQuestion = updateOrSaveSpeedQuestionFromRow(failMap,row);
			}
		}
		return failMap;
	}
	
	private ObjectiveQuestion updateOrSaveQuestionFromRow(Map<Integer,String> map,Row row){
		String type = ExcelUtil.getStringFromExcelCell(row.getCell(0));
		String topic = ExcelUtil.getStringFromExcelCell(row.getCell(1));
		String isbn = ExcelUtil.getStringFromExcelCell(row.getCell(2));
		Book book = bookRepository.findByIsbn(isbn);
		if(book == null){
			map.put(row.getRowNum(), "can't find book with isbn:"+isbn);
			return null;
		}
		if(topic.length()>500){
			map.put(row.getRowNum(), "Question content is too long, the lenth limit 0-500");
			return null;
		}
		
		QuestionType questionType = null;
		if("词汇测试".equals(type)){
			questionType = QuestionType.WORD;
		}else if("验证测试".equals(type)){
			questionType = QuestionType.VERIFY;
		}else{
			questionType = QuestionType.CAPACITY;
		}
		ObjectiveQuestion question = repository.findByTopicAndBookIdAndObjectiveType(topic,book.getId(),questionType);
		 
		if(question == null){
			question = new ObjectiveQuestion();
		}
		
		question.setTopic(topic);
		question.setObjectiveType(questionType);
		question.setBookId(book.getId());
		return repository.save(question);
	}
	
	private SpeedQuestion updateOrSaveSpeedQuestionFromRow(Map<Integer,String> map,Row row){
		String type = ExcelUtil.getStringFromExcelCell(row.getCell(0));
		String topic = ExcelUtil.getStringFromExcelCell(row.getCell(1));
		long articleId = ExcelUtil.getLongFromExcelCell(row.getCell(2));
		if(topic.length()>500){
			map.put(row.getRowNum(), "Question content is too long, the lenth limit 0-500");
			return null;
		}
		
		QuestionType questionType = QuestionType.SPEED;
		
		SpeedQuestion question = speedRepository.findByTopicAndArticleId(topic, articleId);
		 
		if(question == null){
			question = new SpeedQuestion();
		}
		
		question.setTopic(topic);
		question.setObjectiveType(questionType);
		question.setBookId(0L);
		question.setArticleId(articleId);
		return repository.save(question);
	}
	
	private void updateOrSaveOptionFromRow(Map<Integer,String>map,Row row,ObjectiveQuestion currentQuestion){
		if(currentQuestion == null){
			map.put(row.getRowNum(), "Insert Error:Can't connect this option with question");
			return;
		}
		String content = ExcelUtil.getStringFromExcelCell(row.getCell(1));
		String tag = ExcelUtil.getStringFromExcelCell(row.getCell(2));
		Option option = new Option();
		option.setContent(content);
		List<Option> options = currentQuestion.getOptions();
		option.setTag(tag);
		if(options!=null){
			for(int i=0;i<options.size();i++){
				if(options.get(i).getTag().equals(tag)){
					option = options.get(i);
					break;
				}
			}
		}else{
			options = new ArrayList<Option>();
		}
		
		option.setContent(content);
		options.add(option);
		String isCorrectAnswer = ExcelUtil.getStringFromExcelCell(row.getCell(3));
		if("true".equalsIgnoreCase(isCorrectAnswer.trim())){
			currentQuestion.setCorrectAnswer(option);
		}
		currentQuestion.setOptions(options);
		repository.save(currentQuestion);
	}

	@Override
	public Page<ObjectiveQuestion> searchObjectiveQuestionByTopic(String topic,
			Pageable pageable) {
		return repository.findByTopicContaining(topic, pageable);
	}
	 
 
}
