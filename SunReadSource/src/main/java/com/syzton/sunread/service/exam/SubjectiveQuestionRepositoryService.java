package com.syzton.sunread.service.exam;

import java.util.HashMap;
import java.util.Map;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.exam.SubjectQuestionWithBookName;
import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.exam.SubjectiveQuestionRepository;
import com.syzton.sunread.util.ExcelUtil;

@Service
public class SubjectiveQuestionRepositoryService implements SubjectiveQuestionService{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SubjectiveQuestionRepositoryService.class);
	private SubjectiveQuestionRepository repository;
	
	private BookRepository bookRepository;

	@Autowired
	public SubjectiveQuestionRepositoryService(
			SubjectiveQuestionRepository repository,BookRepository bookRepository) {
		this.repository = repository;
		this.bookRepository = bookRepository;
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
		Long bookId = added.getBookId();
		Book book = bookRepository.findOne(bookId);
		book.getExtra().setHasThinkTest(true);
		bookRepository.save(book);
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
		model.setBookId(updated.getBookId());
		model.setQuestionType(updated.getQuestionType());

		return model;
	}
	
	@Transactional
	@Override
	public Map<Integer, String> batchSaveOrUpdateSubjectQuestionFromExcel(
			Sheet sheet) {
		Map<Integer,String> failMap = new HashMap<Integer,String>();
		int total = sheet.getPhysicalNumberOfRows();
		for(int i=sheet.getFirstRowNum()+1;i<total-1;i++){
			Row row = sheet.getRow(i);
			if(row.getCell(0) == null){
				break;
			}
			
			
		 
			String type = ExcelUtil.getStringFromExcelCell(row.getCell(0));
			SubjectiveQuestionType questionType = SubjectiveQuestionType.valueOf(type.toUpperCase());
			String isbn = ExcelUtil.getStringFromExcelCell(row.getCell(2));
			Book book = bookRepository.findByIsbn(isbn);
			String topic = ExcelUtil.getStringFromExcelCell(row.getCell(1));
			Cell updateCell = row.getCell(3);
			String update = "";
			if(updateCell!=null){
				update = ExcelUtil.getStringFromExcelCell(updateCell);
			}
			
			if(book == null){
				failMap.put(i+1, "Can't find book with isbn:"+isbn);
				continue;
			}
			SubjectiveQuestion question = repository.findByTopicAndBookIdAndQuestionType(topic, book.getId(), questionType);
			LOGGER.debug("################question:"+question);
			LOGGER.debug("##############update:"+update);
			if("".equals(update)&& question == null){
				 question = new SubjectiveQuestion();
			}else if(question == null && !"".equals(update)){
				question = new SubjectiveQuestion();
				topic = update;
			}else if(question !=null&&!"".equals(update)){
				topic = update;
			}else{
				failMap.put(i+1,"duplicate insert record.");
				continue;
			}
			question.setBookId(book.getId());
			question.setQuestionType(questionType);
			question.setTopic(topic);
			LOGGER.debug("saveQuestion");
			book.getExtra().setHasThinkTest(true);
			bookRepository.save(book);
			repository.save(question);
		}
		
		return failMap;
	}

	
}
