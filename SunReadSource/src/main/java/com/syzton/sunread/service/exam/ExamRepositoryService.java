package com.syzton.sunread.service.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.CapacityQuestion.CapacityQuestionType;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion.QuestionType;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.exam.CapacityQuestionRepository;
import com.syzton.sunread.repository.exam.ExamRepository;
import com.syzton.sunread.repository.exam.ObjectiveQuestionRepository;
import com.syzton.sunread.repository.exam.SubjectiveQuestionRepository;

@Service
public class ExamRepositoryService implements ExamService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExamRepositoryService.class);

	private ExamRepository repository;

	private ObjectiveQuestionRepository objectQsRepo;

	private SubjectiveQuestionRepository subjectQsRepo;

	private CapacityQuestionRepository capacityQsRepo;

	private BookRepository bookRepo;

	@Autowired
	public ExamRepositoryService(ExamRepository repository,
			ObjectiveQuestionRepository objectQsRepo,SubjectiveQuestionRepository 
			subjectQsRepo,CapacityQuestionRepository capacityQsRepo,
			BookRepository bookRepo) {
		this.repository = repository;
		this.objectQsRepo = objectQsRepo;
		this.subjectQsRepo = subjectQsRepo;
		this.bookRepo = bookRepo;
		this.capacityQsRepo = capacityQsRepo;
	}

	@Override
	public Exam add(Exam added) {
		LOGGER.debug("Adding a new Book entry with information: {}", added);
		return repository.save(added);
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Exam deleteById(Long id) throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		Exam deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;

	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<Exam> findAll(Pageable pageable) throws NotFoundException {

		Page<Exam> examPages = repository.findAll(pageable);

		return examPages;

	}

	@Transactional(readOnly = true, rollbackFor = { NotFoundException.class })
	@Override
	public Exam findById(Long id) throws NotFoundException {
		LOGGER.debug("Finding a book entry with id: {}", id);

		Exam found = repository.findOne(id);
		LOGGER.debug("Found book entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No exam found with id: " + id);
		}

		return found;
	}
	
	

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Exam handInVerifyPaper(Exam added) {
		Exam exam = add(added);
		Set<Answer> answers = exam.getAnswers();
		for (Answer answer : answers) {
			ObjectiveAnswer objectAnswer = (ObjectiveAnswer) answer;
			if (objectAnswer.isCorrect()) {
				exam.setPassCount(exam.getPassCount() + 1);
			} else {
				exam.setFailCount(exam.getFailCount() + 1);
			}

		}
		int score = exam.getPassCount() * 100
				/ (exam.getPassCount() + exam.getFailCount());
		exam.setExamScore(score);
		if (score >= 60) {
			exam.setPass(true);
		} else {
			exam.setPass(false);
		}

		return exam;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Exam handInCapacityTest(Exam added) {
		Exam exam = add(added);
		Set<Answer> answers = exam.getAnswers();
		for (Answer answer : answers) {
			ObjectiveAnswer objectAnswer = (ObjectiveAnswer) answer;
			if (objectAnswer.isCorrect()) {
				exam.setPassCount(exam.getPassCount() + 1);
			} else {
				exam.setFailCount(exam.getFailCount() + 1);
			}

		}
		int score = exam.getPassCount() * 100
				/ (exam.getPassCount() + exam.getFailCount());
		exam.setExamScore(score);
		if (score >= 60) {
			exam.setPass(true);
		} else {
			exam.setPass(false);
		}

		return exam;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Exam handInThinkTest(Exam added) {
		return add(added);
	}

	@Override
	public List<ObjectiveQuestion> takeVerifyTest(final Long bookId) {
		return getRandomObjectiveQuestions(bookId,QuestionType.VERIFY);

	}

	@Override
	public List<CapacityQuestion> takeCapacityTest(int level) {
		List<CapacityQuestionType> list = new ArrayList<CapacityQuestionType>();
		list.add(CapacityQuestionType.FIRST);
		list.add(CapacityQuestionType.SECOND);
		list.add(CapacityQuestionType.THIRD);
		list.add(CapacityQuestionType.FOURTH);
		list.add(CapacityQuestionType.FIFTH);
		List<CapacityQuestion> questions = this.getRandomCapacityQuestion(list, Exam.EXAM_CAPACITY_QUESTION_PER_TYPE,level);
		return questions;
	}

	@Override
	public List<SubjectiveQuestion> takeThinkTest(Long bookId) {
		List<SubjectiveQuestionType> list = new ArrayList<SubjectiveQuestionType>();
		list.add(SubjectiveQuestionType.FIRST);
		list.add(SubjectiveQuestionType.SECOND);
		list.add(SubjectiveQuestionType.THIRD);
		list.add(SubjectiveQuestionType.FOURTH);
		list.add(SubjectiveQuestionType.FIFTH);
		// TODO read num from database
		List<SubjectiveQuestion> questions = this.getRandomSubjectiveQuestion(
				bookId, list, Exam.EXAM_SUBJECTIVE_QUESTION_PER_TYPE);

		return questions;
	}

	private List<ObjectiveQuestion> getRandomObjectiveQuestions(
			final Long bookId,final QuestionType questionType) {
		 
		long total = objectQsRepo.count(new Specification<ObjectiveQuestion>() {

			@Override
			public Predicate toPredicate(Root<ObjectiveQuestion> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				root = query.from(ObjectiveQuestion.class);
				Path<Book> book = root.get("book");
				
				return cb.equal(book.get("id"), bookId);
			}
		});
		int i = this.getRandomPage((int) total, Exam.EXAM_QUESTION);
		Pageable pageable = new PageRequest(i, Exam.EXAM_QUESTION, new Sort(
				"id"));
		Page<ObjectiveQuestion> pageResult = objectQsRepo.findAll(
				new Specification<ObjectiveQuestion>() {

					@Override
					public Predicate toPredicate(Root<ObjectiveQuestion> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						root = query.from(ObjectiveQuestion.class);
						Path<Book> book = root.get("book");
						Path<QuestionType> type = root.get("type");
						return cb.and(cb.equal(book.get("id"), bookId),cb.equal(type,questionType));
					}
				}, pageable);
		List<ObjectiveQuestion> list = pageResult.getContent();
		return list;
	}

	private int getRandomPage(int total, int size) {
		if (total <= size) {
			return 0;
		}
		Random random = new Random();
		int randomPage = random.nextInt(total / size);
		return randomPage;
	}

	private List<SubjectiveQuestion> getRandomSubjectiveQuestion(Long bookId,
			List<SubjectiveQuestionType> typeList, int num) {
		List<SubjectiveQuestion> list = new ArrayList<SubjectiveQuestion>();
		 
		Random random = new Random();
		if (typeList != null) {
			for (int i = 0; i < typeList.size(); i++) {
				List<SubjectiveQuestion> tempList = subjectQsRepo
						.findByQuestionTypeAndBookId(typeList.get(i), bookId);
				if (tempList.size() > num) {
					int z = random.nextInt(tempList.size());
					for (int j = 0; j < num; j++) {
						list.add(tempList.get(z));
						z = (z + 1) % tempList.size();
					}
				} else {
					list.addAll(tempList);
				}
			}
		}
		return list;
	}
	
	private List<CapacityQuestion> getRandomCapacityQuestion(
			List<CapacityQuestionType> typeList, int num,int level) {
		List<CapacityQuestion> list = new ArrayList<CapacityQuestion>();
		 
		Random random = new Random();
		if (typeList != null) {
			for (int i = 0; i < typeList.size(); i++) {
				List<CapacityQuestion> tempList = capacityQsRepo
						.findByQuestionTypeAndLevel(typeList.get(i),level);
				if (tempList.size() > num) {
					int z = random.nextInt(tempList.size());
					for (int j = 0; j < num; j++) {
						list.add(tempList.get(z));
						z = (z + 1) % tempList.size();
					}
				} else {
					list.addAll(tempList);
				}
			}
		}
		return list;
	}

	@Override
	public List<Exam> getTodayVerifyTestStatus(Long bookId, Long studentId) {	
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = cal.getTime();
		 
		List<Exam> list = repository.findByStudentIdAndBookIdAfter(studentId, bookId,date);
		return list;
	}
	
	public boolean isPassVerifyTest(Long bookId,Long studentId){
		 
		List<Exam> list = repository.findByStudentIdAndBookId(bookId,studentId);
		for(int i=0;i<list.size();i++){
			if(list.get(i).isPass()){
				return true;
			}
		}
		return false;
		
	}

	@Override
	public List<ObjectiveQuestion> takeWordTest(Long bookId) {
		return getRandomObjectiveQuestions(bookId,QuestionType.WORD);
	}

	@Override
	public List<ObjectiveQuestion> takeSpeedTest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exam handInWordPaper(Exam added) {
		Exam exam = added;
		Set<Answer> answers = exam.getAnswers();
		for (Answer answer : answers) {
			ObjectiveAnswer objectAnswer = (ObjectiveAnswer) answer;
			if (objectAnswer.isCorrect()) {
				exam.setPassCount(exam.getPassCount() + 1);
			} else {
				exam.setFailCount(exam.getFailCount() + 1);
			}

		}
		int score = exam.getPassCount() * 100
				/ (exam.getPassCount() + exam.getFailCount());
		exam.setExamScore(score);
		if (score >= 60) {
			exam.setPass(true);
		} else {
			exam.setPass(false);
		}
		exam = add(exam);
		return exam;
	}

	@Override
	public Exam handInSpeedTest(Exam added) {
		// TODO Auto-generated method stub
		return null;
	}
}
