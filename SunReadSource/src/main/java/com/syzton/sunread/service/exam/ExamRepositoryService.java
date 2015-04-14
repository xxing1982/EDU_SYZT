package com.syzton.sunread.service.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import javassist.NotFoundException;

import org.apache.commons.collections.map.HashedMap;
import org.joda.time.DateTime;
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

import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.dto.exam.VerifiedExamDTO;
import com.syzton.sunread.dto.exam.VerifyExamPassDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Review;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.CapacityExamHistory;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.CapacityQuestion.CapacityQuestionType;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;
import com.syzton.sunread.model.exam.ObjectiveAnswer;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion.QuestionType;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;
import com.syzton.sunread.repository.exam.CapacityExamHistoryRepository;
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
	
	private CapacityExamHistoryRepository capacityExamRepo;
	
 
	
	

	@Autowired
	public ExamRepositoryService(ExamRepository repository,
			ObjectiveQuestionRepository objectQsRepo,SubjectiveQuestionRepository 
			subjectQsRepo,CapacityQuestionRepository capacityQsRepo,CapacityExamHistoryRepository capacityExamRep) {
		this.repository = repository;
		this.objectQsRepo = objectQsRepo;
		this.subjectQsRepo = subjectQsRepo;
		this.capacityQsRepo = capacityQsRepo;
		this.capacityExamRepo = capacityExamRep;
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
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public VerifyExamPassDTO findAllByExamTypeAndPassStatus(Long studentId,ExamType type) throws NotFoundException {
		Exam timeExam = repository.findByStudentIdOrderByCreationTimeDesc(studentId);
		if(timeExam == null){
			throw new NotFoundException("can't find exam record");
		}
		int passCount = 0;
		int questionCount = 0;
		int passRate = 0;
		List<Exam> exams = repository.findByStudentIdAndExamTypeAndIsPass(studentId,type,true);
		List<ExamDTO> examDTOs = new ArrayList<ExamDTO>();
		for(int i=0;i<exams.size();i++){
			Exam exam = exams.get(i);
			passCount = passCount+exam.getPassCount();
			questionCount = questionCount+exam.getQuestionNum();
			if(type.equals(ExamType.VERIFY)){
				Set<Review> reviews = exam.getBook().getReviews();
				VerifiedExamDTO examDTO = new VerifiedExamDTO(exam);
				for(Review review : reviews){
					if(review.getStudentId().equals(exam.getStudentId())){
						examDTO.setComment(review.getContent());
						break;
					}
				}
				examDTOs.add(examDTO);
			
			}else{
				examDTOs.add(new ExamDTO(exam));
			}
		}
		if(questionCount != 0){
			passRate = passCount*100/questionCount;
		}
		String time = timeExam.getCreationTime().toString("yyyy"); 
		VerifyExamPassDTO passDTO = new VerifyExamPassDTO(examDTOs,passRate,time);
		return passDTO;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public VerifyExamPassDTO findAllByExamType(Long studentId,ExamType type) throws NotFoundException {
		Exam timeExam = repository.findByStudentIdOrderByCreationTimeDesc(studentId);
		if(timeExam == null){
			throw new NotFoundException("can't find exam record");
		}
		int passCount = 0;
		int questionCount = 0;
		int passRate = 0;
		List<Exam> exams = repository.findByStudentIdAndExamType(studentId,type);
		List<ExamDTO> examDTOs = new ArrayList<ExamDTO>();
		for(int i=0;i<exams.size();i++){
			Exam exam = exams.get(i);
			passCount = passCount+exam.getPassCount();
			questionCount = questionCount+exam.getQuestionNum();
			if(type.equals(ExamType.VERIFY)){
				Set<Review> reviews = exam.getBook().getReviews();
				VerifiedExamDTO examDTO = new VerifiedExamDTO(exam);
				for(Review review : reviews){
					if(review.getStudentId().equals(exam.getStudentId())){
						examDTO.setComment(review.getContent());
						break;
					}
				}
				examDTOs.add(examDTO);
			
			}else{
				examDTOs.add(new ExamDTO(exam));
			}
		}
		if(questionCount != 0){
			passRate = passCount*100/questionCount;
		}
		String time = timeExam.getCreationTime().toString("yyyy"); 
		VerifyExamPassDTO passDTO = new VerifyExamPassDTO(examDTOs,passRate,time);
		return passDTO;
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
		CapacityExamHistory capacityHistory = new CapacityExamHistory();
		for (Answer answer : answers) {
			ObjectiveAnswer objectAnswer = (ObjectiveAnswer) answer;
			updateCapacityHistory(capacityHistory,objectAnswer);
			if (objectAnswer.isCorrect()) {
				exam.setPassCount(exam.getPassCount() + 1);
			} else {
				exam.setFailCount(exam.getFailCount() + 1);
			}

		}
		int score = exam.getPassCount() * 100
				/ (exam.getPassCount() + exam.getFailCount());
		exam.setQuestionNum(added.getQuestions().size());
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
		list.add(CapacityQuestionType.SIXTH);
		list.add(CapacityQuestionType.SEVENTH);
		list.add(CapacityQuestionType.EIGHTTH);
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
				Path<Long> book = root.get("bookId");
				
				return cb.equal(book, bookId);
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
						Path<Book> book = root.get("bookId");
						Path<QuestionType> type = root.get("objectiveType");
						return cb.and(cb.equal(book, bookId),cb.equal(type,questionType));
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
		DateTime dt = new DateTime(date);    
		List<Exam> list = repository.findByStudentIdAndBookIdAndCreationTimeAfter(studentId, bookId,dt);
		return list;
	}
	
	 
	
	public boolean isPassVerifyTest(Long bookId,Long studentId){
		 
		List<Exam> list = repository.findByStudentIdAndBookId(studentId,bookId);
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
	
	@Transactional
	@Override
	public int findPassVerifyExamPassRate(Long studentId, DateTime from,DateTime to) {
		List<Exam> exams = repository.findByStudentIdAndExamTypeAndIsPassAndCreationTimeBetween(studentId, ExamType.VERIFY, true, from, to);
		int pass = 0;
		int total = 0;
		int passRate = 0;
		for(int i=0;i<exams.size();i++){
			Exam exam = exams.get(i);
			pass = pass + exam.getPassCount();
			total = total + exam.getQuestionNum();
		}
		
		if(total!=0){
			passRate = pass*100/total;
		}
		return passRate;
	}

	@Transactional
	@Override
	public Map<CapacityQuestionType, Integer> getStudentCapacityStatus(
			Long studentId, DateTime from, DateTime to) {
		List<CapacityExamHistory> capacityExams = capacityExamRepo.findByStudentIdAndCreationTimeBetween(studentId, from, to);
		Map<CapacityQuestionType, Integer> map = new HashMap<CapacityQuestionType, Integer>();
		int firstPass = 0;
		int firstCount = 0;
		
		int secondPass = 0;
		int secondCount = 0;
		
		int thirdPass = 0;
		int thirdCount = 0;
		
		int fourthPass = 0;
		int fourthCount = 0; 
		
		int fifthPass = 0;
		int fifthCount = 0;
		
		int sixthPass = 0;
		int sixthCount = 0;
		
		int seventhPass = 0;
		int seventhCount = 0;
		
		int eightthPass = 0;
		int eightthCount = 0;
		
		for(int i=0;i<capacityExams.size();i++){
			 CapacityExamHistory exam = capacityExams.get(i);
			 firstPass = firstPass + exam.getFirstPass();
			 firstCount = firstCount + exam.getFirstFail()+exam.getFirstPass();
			 
			 secondPass = secondPass + exam.getSecondPass();
			 secondCount = secondCount + exam.getSecondFail()+exam.getSecondPass();
			
			 thirdPass = thirdPass + exam.getThirdPass();
			 thirdCount = thirdCount + exam.getThirdFail()+exam.getThirdPass();
			 
			 fourthPass = fourthPass + exam.getFourthPass();
			 fourthCount = fourthCount + exam.getFourthFail()+exam.getFourthPass();
		
			 fifthPass = fifthPass + exam.getFifthPass();
			 fifthCount = fifthCount + exam.getFifthFail()+exam.getFifthPass();
			 
			 sixthPass = sixthPass + exam.getSixthPass();
			 sixthCount = sixthCount + exam.getSixthFail()+exam.getSixthPass();
			 
			 seventhPass = seventhPass + exam.getSeventhPass();
			 seventhCount = seventhCount + exam.getSeventhFail()+exam.getSeventhPass();
			 
			 eightthPass = eightthPass + exam.getEightthPass();
			 eightthCount = eightthCount + exam.getEightthFail()+exam.getEightthPass();
		}
		map.put(CapacityQuestionType.FIRST, firstCount==0?0:firstPass*100/firstCount);
		map.put(CapacityQuestionType.SECOND, secondCount==0?0:secondPass*100/secondCount);
		map.put(CapacityQuestionType.THIRD, thirdCount==0?0:thirdPass*100/thirdCount);
		map.put(CapacityQuestionType.FOURTH, fourthCount==0?0:fourthPass*100/fourthCount);
		map.put(CapacityQuestionType.FIFTH, fifthCount==0?0:fifthPass*100/fifthCount);
		map.put(CapacityQuestionType.SIXTH, sixthCount==0?0:sixthPass*100/sixthCount);
		map.put(CapacityQuestionType.SEVENTH, seventhCount==0?0:seventhPass*100/seventhCount);
		map.put(CapacityQuestionType.EIGHTTH, eightthCount==0?0:eightthPass*100/eightthCount);
		return map;
	}
	
	
	
	private void updateCapacityHistory(CapacityExamHistory history,ObjectiveAnswer answer){
		CapacityQuestion question = (CapacityQuestion) answer.getQuestion();
		CapacityQuestionType type = question.getQuestionType();
		if(type.equals(CapacityQuestionType.FIRST)){
			if(answer.isCorrect()){
				history.setFirstPass(history.getFirstPass()+1);
			}else{
				history.setFirstFail(history.getFirstFail()+1);
			}
		}else if(type.equals(CapacityQuestionType.SECOND)){
			if(answer.isCorrect()){
				history.setSecondPass(history.getSecondPass()+1);
			}else{
				history.setSecondFail(history.getSecondFail()+1);
			}
		}else if(type.equals(CapacityQuestionType.THIRD)){
			if(answer.isCorrect()){
				history.setThirdPass(history.getThirdPass()+1);
			}else{
				history.setThirdFail(history.getThirdFail()+1);
			}
		}else if(type.equals(CapacityQuestionType.FOURTH)){
			if(answer.isCorrect()){
				history.setFourthPass(history.getFourthPass()+1);
			}else{
				history.setFourthFail(history.getFourthFail()+1);
			}
		}else if(type.equals(CapacityQuestionType.FIFTH)){
			if(answer.isCorrect()){
				history.setFifthPass(history.getFifthPass()+1);
			}else{
				history.setFifthFail(history.getFifthFail()+1);
			}
		}else if(type.equals(CapacityQuestionType.SIXTH)){
			if(answer.isCorrect()){
				history.setSixthPass(history.getSixthPass()+1);
			}else{
				history.setSixthFail(history.getSixthFail()+1);
			}
		 
		}else if(type.equals(CapacityQuestionType.SEVENTH)){
			if(answer.isCorrect()){
				history.setSeventhPass(history.getSeventhPass()+1);
			}else{
				history.setSeventhFail(history.getSeventhFail()+1);
			}
		}else if(type.equals(CapacityQuestionType.EIGHTTH)){
			if(answer.isCorrect()){
				history.setEightthPass(history.getEightthPass()+1);
			}else{
				history.setEightthFail(history.getEightthFail()+1);
			}
		}
	}
}
