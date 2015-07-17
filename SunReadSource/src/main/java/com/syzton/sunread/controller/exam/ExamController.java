package com.syzton.sunread.controller.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.NotFoundException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.exam.CapacityPaperDTO;
import com.syzton.sunread.dto.exam.CollatExamDTO;
import com.syzton.sunread.dto.exam.SpeedPaperExamDTO;
import com.syzton.sunread.dto.exam.SpeedTestRecordDTO;
import com.syzton.sunread.dto.exam.SubjectivePaperDTO;
import com.syzton.sunread.dto.exam.VerifyExamPassDTO;
import com.syzton.sunread.dto.exam.VerifyPaperDTO;
import com.syzton.sunread.dto.exam.VerifyPaperExamDTO;
import com.syzton.sunread.exception.bookshelf.BookInShelfDuplicateVerifiedException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.coinhistory.CoinHistory.CoinFrom;
import com.syzton.sunread.model.coinhistory.CoinHistory.CoinType;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Article;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.CapacityQuestion.CapacityQuestionType;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.SpeedTestRecord;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.ClazzCategoryCount;
import com.syzton.sunread.model.pointhistory.PointHistory;
import com.syzton.sunread.model.pointhistory.PointHistory.PointFrom;
import com.syzton.sunread.model.pointhistory.PointHistory.PointType;
import com.syzton.sunread.model.semester.Semester;
import com.syzton.sunread.model.user.CategoryCount;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.book.DictionaryRepository;
import com.syzton.sunread.repository.exam.SpeedTestRecordRepository;
import com.syzton.sunread.repository.organization.ClazzRepository;
import com.syzton.sunread.service.book.BookService;
import com.syzton.sunread.service.book.TestPassService;
import com.syzton.sunread.service.bookshelf.BookInShelfService;
import com.syzton.sunread.service.coinhistory.CoinHistoryService;
import com.syzton.sunread.service.exam.ArticleService;
import com.syzton.sunread.service.exam.ExamService;
import com.syzton.sunread.service.organization.CampusService;
import com.syzton.sunread.service.pointhistory.PointHistoryService;
import com.syzton.sunread.service.semester.SemesterService;
import com.syzton.sunread.service.user.UserService;

@Controller
@RequestMapping(value = "/api")
public class ExamController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExamController.class);

	private ExamService service;

	private TestPassService testPassService;

	private CoinHistoryService coinService;

	private PointHistoryService pointService;

	private UserService userService;

	private BookService bookService;

	private BookInShelfService shelfService;
	
	private SemesterService semesterService;

	private ClazzRepository clazzRepository;

	private DictionaryRepository dictionaryRepository;
	
	private SpeedTestRecordRepository srRepo;
	
	private ArticleService articleService;
	
	private CampusService campusService;
	
	

	@Autowired
	public ExamController(ExamService service, TestPassService tService,
			CoinHistoryService coinService, PointHistoryService pointService,
			UserService userService, BookService bookService,
			BookInShelfService shelfService,
			ClazzRepository clazzRepository,
			DictionaryRepository dictionaryRepository,
			SemesterService semesterService,
			SpeedTestRecordRepository srRepo,
			ArticleService articleService,
			CampusService campusService) {
		this.service = service;
		this.testPassService = tService;
		this.coinService = coinService;
		this.pointService = pointService;
		this.userService = userService;
		this.bookService = bookService;
		this.shelfService = shelfService;
		this.semesterService = semesterService;
		this.clazzRepository = clazzRepository;
		this.dictionaryRepository = dictionaryRepository;
		this.srRepo = srRepo;
		this.articleService=articleService;
		this.campusService = campusService;
	}

	@RequestMapping(value = "/exam", method = RequestMethod.POST)
	@ResponseBody
	public Exam add(@Valid @RequestBody Exam dto) {
		LOGGER.debug("Adding a new exam entry with information: {}", dto);

		Exam added = service.add(dto);
		LOGGER.debug("Added a exam entry with information: {}", added);

		return added;
	}

	@RequestMapping(value = "/exam/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Exam deleteById(@PathVariable("id") Long id)
			throws NotFoundException {
		LOGGER.debug("Deleting a exam entry with id: {}", id);

		Exam deleted = service.deleteById(id);
		LOGGER.debug("Deleted exam entry with information: {}", deleted);

		return deleted;
	}

	@RequestMapping(value = "/exam", method = RequestMethod.GET)
	@ResponseBody
	public PageResource<Exam> findAll(@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("sortBy") String sortBy) throws NotFoundException {
		LOGGER.debug("Finding all exam entries.");
		sortBy = sortBy == null ? "id" : sortBy;
		Pageable pageable = new PageRequest(page, size, new Sort(sortBy));
		Page<Exam> pageResult = service.findAll(pageable);
		LOGGER.debug("Found {} exam entries.", pageResult.getTotalElements());

		return new PageResource<>(pageResult, "page", "size");
	}
	
	
	@RequestMapping(value = "/exam/verifypaper/{studentid}/{bookid}", method = RequestMethod.GET)
	@ResponseBody
	public VerifyPaperDTO createVerifyPaper(
			@PathVariable("studentid") Long studentId,
			@PathVariable("bookid") Long bookId) throws NotFoundException {
		LOGGER.debug("Finding all exam entries.");
		VerifyPaperDTO dto = new VerifyPaperDTO();
		if (service.isPassVerifyTest(bookId, studentId)) {
			dto.setCode("2");
			dto.setMessage("Student " + studentId
					+ " have verified the book " + bookId);
			return dto;
		}
		List<Exam> list = service.getTodayVerifyTestStatus(bookId, studentId);
		if (list.size() >= 2) {
			dto.setCode("3");
			dto.setMessage(
					"Student{"
							+ studentId
							+ "} verify test with book{"
							+ bookId
							+ "} greater than twice, system ignore this verify test request.");
			return dto;
		}
		List<ObjectiveQuestion> questions = service.takeVerifyTest(bookId);
		dto.setQuestions(questions);
		dto.setCode("1");
		dto.setMessage("Get questions complete.");
		LOGGER.debug("Found {} exam entries.", questions.size());

		return dto;
	}

	@RequestMapping(value = "/exam/wordpaper/{studentid}/{bookid}", method = RequestMethod.GET)
	@ResponseBody
	public List<ObjectiveQuestion> createWordPaper(
			@PathVariable("studentid") Long studentId,
			@PathVariable("bookid") Long bookId) throws NotFoundException {
		LOGGER.debug("Finding all exam entries.");
		List<ObjectiveQuestion> questions = service.takeWordTest(bookId);
		LOGGER.debug("Found {} exam entries.", questions.size());
		return questions;
	}
	
	

	@RequestMapping(value = "/exam/capacitypaper/{level}", method = RequestMethod.GET)
	@ResponseBody
	public List<CapacityQuestion> createCapacityPaper(
			@PathVariable("level") int level) throws NotFoundException {
		LOGGER.debug("Finding all todo entries.");

		List<CapacityQuestion> questions = service.takeCapacityTest(level);
		LOGGER.debug("Found {} exam entries.", questions.size());

		return questions;
	}

	@RequestMapping(value = "/exam/thinkpaper/{bookid}", method = RequestMethod.GET)
	@ResponseBody
	public List<SubjectiveQuestion> createThinkPaper(
			@PathVariable("bookid") Long bookId) throws NotFoundException {
		LOGGER.debug("Finding all exam entries.");

		List<SubjectiveQuestion> questions = service.takeThinkTest(bookId);
		LOGGER.debug("Found {} exam entries.", questions.size());

		return questions;
	}

	@RequestMapping(value = "/exam/verifypaper", method = RequestMethod.POST)
	@ResponseBody
	public CollatExamDTO handInVerifyPaper(@Valid @RequestBody VerifyPaperExamDTO dto)
			throws NotFoundException, BookInShelfDuplicateVerifiedException {
		Exam exam = dto.fromOTD();
		CollatExamDTO collatExamDto = new CollatExamDTO();
		LOGGER.debug("hand in exam entrie.");
		long studentId = exam.getStudentId();
		long bookId = exam.getBook().getId();
		if (service.isPassVerifyTest(bookId, studentId)) {
			collatExamDto.setCode("2");
			collatExamDto.setMessage("Student " + studentId
					+ " have verified the book " + bookId);
			return collatExamDto;
		}
		List<Exam> list = service.getTodayVerifyTestStatus(studentId, bookId);
		
		if (list.size() >= 2) {
			collatExamDto.setCode("3");
			collatExamDto.setMessage(
					"Student{"
							+ studentId
							+ "} verify test with book{"
							+ bookId
							+ "} greater than twice, system ignore this verify test request.");
			return collatExamDto;
		}
		Exam examResult = service.handInVerifyPaper(exam);
		Student student = userService.findByStudentId(studentId);
		student.getStatistic().increaseTestPasses();
		if (examResult.isPass()) {
			testPassService.hotBookUpdate(bookId, studentId);

			Book book = bookService.findById(bookId);
			CoinHistory coinHistory = new CoinHistory();
			coinHistory.setCoinFrom(CoinFrom.FROM_VERIFY_TEST);
			coinHistory.setCoinType(CoinType.IN);
			coinHistory.setNum(book.getCoin());
			coinHistory.setStudent(student);
			coinService.add(coinHistory);
			
			
			PointHistory pointHistory = new PointHistory();
			pointHistory.setPointFrom(PointFrom.FROM_VERIFY_TEST);
			pointHistory.setPointType(PointType.IN);
			pointHistory.setNum(book.getPoint());
			pointHistory.setStudent(student);
			pointService.add(pointHistory);

			student.getStatistic().setCoin(
					student.getStatistic().getCoin() + book.getCoin());
			student.getStatistic().setPoint(
					student.getStatistic().getPoint() + book.getPoint());
			student.getStatistic().increaseTestPasses();

			Dictionary dictionary = dictionaryRepository.findByTypeAndValue(DictionaryType.CATEGORY,book.getExtra().getCategory());

			Set<CategoryCount> categoryCounts = student.getCategoryCount();
			if(categoryCounts.size()>0){
				for(CategoryCount categoryCount : categoryCounts){
					if(categoryCount.getDictionary().getId() == dictionary.getId()){
						categoryCount.increaseCount();
						categoryCounts.add(categoryCount);
					}
				}
			}else{
				CategoryCount categoryCount = new CategoryCount();
				categoryCount.increaseCount();
				categoryCount.setDictionary(dictionary);
				categoryCounts.add(categoryCount);
			}


			Clazz clazz = clazzRepository.findOne(student.getClazzId());
			clazz.getClazzStatistic().increaseTotalReads();
			clazz.getClazzStatistic().setTotalReadWords(clazz.getClazzStatistic().getTotalReadWords() + book.getWordCount());

			Set<ClazzCategoryCount> clazzCategoryCounts = clazz.getCategoryCount();
			if(clazzCategoryCounts.size()>0){
				for(ClazzCategoryCount categoryCount : clazzCategoryCounts){
					if(categoryCount.getDictionary().getId() == dictionary.getId()){
						categoryCount.increaseCount();
						clazzCategoryCounts.add(categoryCount);
					}
				}
			}else{
				List<Dictionary> categoryDics = dictionaryRepository.findByType(DictionaryType.CATEGORY);
				for(Dictionary categoryDic : categoryDics){
					if (categoryDic.getId() == 0) continue;
					ClazzCategoryCount categoryCount = new ClazzCategoryCount();
					categoryCount.setDictionary(categoryDic);
					if(categoryDic.getId()==dictionary.getId() ){
						categoryCount.increaseCount();
					}
					clazzCategoryCounts.add(categoryCount);
				}

			}

			clazzRepository.save(clazz);

			shelfService.updateReadState(studentId, bookId);

		}
		userService.saveStudent(student);
		LOGGER.debug("return a exam entry result with information: {}", exam);
		collatExamDto.setCode("1");
		collatExamDto.setExam(exam);
		collatExamDto.setMessage("Exam collat complete.");
		return collatExamDto;
	}

	@RequestMapping(value = "/exam/wordpaper", method = RequestMethod.POST)
	@ResponseBody
	public Exam handInWordPaper(@Valid @RequestBody VerifyPaperExamDTO dto)
			throws NotFoundException {
		Exam exam = dto.fromOTD();
		LOGGER.debug("hand in exam entrie.");

		Exam examResult = service.handInWordPaper(exam);

		LOGGER.debug("return a exam entry result with information: {}", exam);

		return examResult;
	}
	
	@RequestMapping(value = "/exam/speedpaper", method = RequestMethod.POST)
	@ResponseBody
	public Exam handInSpeedPaper(@Valid @RequestBody SpeedPaperExamDTO dto)
			throws NotFoundException {
		Exam exam = dto.fromOTD();
		LOGGER.debug("hand in exam entrie.");

		Exam examResult = service.handInSpeedTest(exam);
		if(examResult.isPass()){
			SpeedTestRecord testRecord = new SpeedTestRecord();
			testRecord.setArticleId(exam.getArticleId());
			Article article = articleService.getArticle(exam.getArticleId());
			int length = article.getContent().length();
			testRecord.setTime(dto.getTime());
			testRecord.setSpeed(length*60/dto.getTime());
			testRecord.setUserId(examResult.getStudentId());
			testRecord.setScore(examResult.getExamScore());
			Student student = userService.findByStudentId(examResult.getStudentId());
			testRecord.setSchoolId(student.getCampusId());
			srRepo.save(testRecord);
		}
		

		LOGGER.debug("return a exam entry result with information: {}", exam);

		return examResult;
	}
	
	@RequestMapping(value = "/speedlist/{num}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpeedTestRecordDTO> speedList(@PathVariable("num") int num)
			throws NotFoundException {
		List<SpeedTestRecord> srs = srRepo.getTopStudent(num);
		List<SpeedTestRecordDTO> dtos = new ArrayList<SpeedTestRecordDTO>();
		for(int i=0;i<srs.size();i++){
			SpeedTestRecord sr = srs.get(i);
			SpeedTestRecordDTO dto = new SpeedTestRecordDTO();
			dto.setArticleId(sr.getArticleId());
			dto.setId(sr.getId());
			long schoolId = sr.getSchoolId();
			dto.setSchoolName(campusService.findById(schoolId).getName());
			dto.setSchoolId(schoolId);
			dto.setScore(sr.getScore());
			dto.setSpeed(sr.getSpeed());
			dto.setTime(sr.getTime());
			long userId = sr.getUserId();
			dto.setUserId(userId);
			dto.setUserName(userService.findByStudentId(userId).getUsername());
			dtos.add(dto);
		}
		return dtos;
	}
	
	@RequestMapping(value = "/speedschoollist/{schoolid}/{num}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpeedTestRecordDTO> speedListFromSchool(@PathVariable("schoolid") long schoolId,@PathVariable("num") int num)
			throws NotFoundException {
		List<SpeedTestRecord> srs = srRepo.getTopStudentBySchool(num, schoolId);
		List<SpeedTestRecordDTO> dtos = new ArrayList<SpeedTestRecordDTO>();
		
		String schoolName = campusService.findById(schoolId).getName();
		for(int i=0;i<srs.size();i++){
			SpeedTestRecord sr = srs.get(i);
			SpeedTestRecordDTO dto = new SpeedTestRecordDTO();
			dto.setArticleId(sr.getArticleId());
			dto.setId(sr.getId());
			dto.setSchoolId(schoolId);
			dto.setSchoolName(schoolName);
			dto.setSchoolId(schoolId);
			dto.setScore(sr.getScore());
			dto.setSpeed(sr.getSpeed());
			dto.setTime(sr.getTime());
			long userId = sr.getUserId();
			dto.setUserId(userId);
			dto.setUserName(userService.findByStudentId(userId).getUsername());
			dtos.add(dto);
		}
		return dtos;

	}
	
	@RequestMapping(value = "/speedpersonlist/{userid}/{num}", method = RequestMethod.GET)
	@ResponseBody
	public List<SpeedTestRecord> speedListPersonal(@PathVariable("userid") long userId,@PathVariable("num") int num)
			throws NotFoundException {
		List<SpeedTestRecord> srs = srRepo.getPersonalHistory(num, userId);
		return srs;
	}

	@RequestMapping(value = "/exam/thinkpaper", method = RequestMethod.POST)
	@ResponseBody
	public Exam handInThinkPaper(@Valid @RequestBody SubjectivePaperDTO paper)
			throws NotFoundException {
		Exam exam = paper.fromOTD();
		Student user = userService.findByStudentId(exam.getStudentId());
		Set<Answer> answers = exam.getAnswers();
		for(Answer answer : answers){
			SubjectiveAnswer subjectAnswer = (SubjectiveAnswer)answer;
			subjectAnswer.setCampusId(user.getCampusId());
		}
		LOGGER.debug("hand in exam entrie.");
		Exam examResult = service.handInThinkTest(exam);
		LOGGER.debug("return a exam entry result with information: {}", exam);

		return examResult;
	}

	@RequestMapping(value = "/exam/capacitypaper", method = RequestMethod.POST)
	@ResponseBody
	public Exam handInCapacityPaper(@Valid @RequestBody CapacityPaperDTO dto)
			throws NotFoundException {
		Exam exam = dto.fromOTD();
		LOGGER.debug("hand in exam entrie.");
		Exam examResult = service.handInCapacityTest(exam);
		LOGGER.debug("return a exam entry result with information: {}", exam);

		return examResult;
	}

	@RequestMapping(value = "/exam/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Exam findById(@PathVariable("id") Long id) throws NotFoundException {
		LOGGER.debug("Finding to-do entry with id: {}", id);

		Exam found = service.findById(id);
		LOGGER.debug("Found to-do entry with information: {}", found);

		return found;
	}

	@RequestMapping(value = "/verifyexams/pass/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public VerifyExamPassDTO findPassVerifyExam(
			@PathVariable("userid") Long userId) throws NotFoundException {
		LOGGER.debug("Finding objective question entry with id: {}");
		VerifyExamPassDTO examPassDTO = service.findAllByExamTypeAndPassStatus(
				userId, ExamType.VERIFY);

		return examPassDTO;
	}

	@RequestMapping(value = "/verifyexams/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public VerifyExamPassDTO findVerifyExams(@PathVariable("userid") Long userId)
			throws NotFoundException {
		LOGGER.debug("Finding objective question entry with id: {}");
		VerifyExamPassDTO examPassDTO = service.findAllByExamType(userId,
				ExamType.VERIFY);
		return examPassDTO;
	}

	@RequestMapping(value = "/wordexams/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public VerifyExamPassDTO findWordExams(@PathVariable("userid") Long userId)
			throws NotFoundException {
		LOGGER.debug("Finding objective question entry with id: {}");
		VerifyExamPassDTO examPassDTO = service.findAllByExamType(userId,
				ExamType.WORD);

		return examPassDTO;
	}

	@RequestMapping(value = "/capacityexams/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public VerifyExamPassDTO findCapacityExams(
			@PathVariable("userid") Long userId) throws NotFoundException {
		LOGGER.debug("Finding objective question entry with id: {}");
		VerifyExamPassDTO examPassDTO = service.findAllByExamType(userId,
				ExamType.CAPACITY);
		return examPassDTO;
	}

	@RequestMapping(value = "/thinkexams/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public VerifyExamPassDTO findThinkExams(@PathVariable("userid") Long userId)
			throws NotFoundException {
		LOGGER.debug("Finding objective question entry with id: {}");
		VerifyExamPassDTO examPassDTO = service.findAllByExamType(userId,
				ExamType.THINK);
		return examPassDTO;
	}
	
	@RequestMapping(value = "/verifyexams/average/{studentid}/{semester}", method = RequestMethod.GET)
	@ResponseBody
	public int getAverageVerifyExamsPassRate(@PathVariable("studentid") Long studentId,@PathVariable("semester") Long semesterId)
			throws NotFoundException {
		Semester semester = semesterService.findOne(semesterId);
		int passRate = service.findPassVerifyExamPassRate(studentId, semester.getStartTime(), semester.getEndTime());
		return passRate;
	}
	
	@RequestMapping(value = "/verifyexams/fistpassrate/{studentid}/{semester}", method = RequestMethod.GET)
	@ResponseBody
	public int getFirstPassExamsPassRate(@PathVariable("studentid") Long studentId,@PathVariable("semester") Long semesterId)
			throws NotFoundException {
		Semester semester = semesterService.findOne(semesterId);
		int passRate = service.findFirstPassRate(studentId, semester.getStartTime(), semester.getEndTime());
		return passRate;
	}
	
	@RequestMapping(value = "/verifyexams/secondpassrate/{studentid}/{semester}", method = RequestMethod.GET)
	@ResponseBody
	public int getSecondPassExamsPassRate(@PathVariable("studentid") Long studentId,@PathVariable("semester") Long semesterId)
			throws NotFoundException {
		Semester semester = semesterService.findOne(semesterId);
		int passRate = service.findSecondPassRate(studentId, semester.getStartTime(), semester.getEndTime());
		return passRate;
	}
	
	@RequestMapping(value = "/verifyexams/passrate/{studentId}/{semesterId}", method = RequestMethod.GET)
	@ResponseBody
	public String getPassRate(@PathVariable("studentId") Long studentId,@PathVariable("semesterId") Long semesterId)
			throws NotFoundException {
		int average = getAverageVerifyExamsPassRate(studentId, semesterId);
		int firstpassrate = getFirstPassExamsPassRate(studentId, semesterId);
		int secondpassrate = getSecondPassExamsPassRate(studentId, semesterId);
		return "{\"average\":" + average +  ",\"firstpassrate\":" + firstpassrate + ",\"secondpassrate\":" + secondpassrate + "}";
	}
	
	@RequestMapping(value = "/capacityexam/everytypepassrate/{studentid}/{semester}", method = RequestMethod.GET)
	@ResponseBody
	public Map<CapacityQuestionType,Integer> getCapacityQuestionPassrate(@PathVariable("studentid") Long studentId,@PathVariable("semester") Long semesterId){
		Semester semester = semesterService.findOne(semesterId);
		Map<CapacityQuestionType,Integer> passRate = service.getStudentCapacityStatus(studentId, semester.getStartTime(), semester.getEndTime());
		return passRate;
	}
	
}
