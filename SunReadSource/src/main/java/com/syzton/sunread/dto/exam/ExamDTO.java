package com.syzton.sunread.dto.exam;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.Exam.ExamType;

public class ExamDTO {
	
	private Long id;
	
	private Set<Question> questions;

	private Long bookId;
	
	private String name;
	
	

	private Date creationTime;

	private String pictureUrl;
	
	private Long studentId;

	private ExamType examType;

	private boolean isPass = false;

	private int examScore;

	private int passCount;

	private int failCount;

	private int questionNum;


	private Set<Answer> answers;

	private long version;
	
	

	public ExamDTO() {

	}

	public ExamDTO(Long id, Set<Question> questions, Long bookId,
			String name, DateTime creationTime, String pictureUrl,
			Long studentId, ExamType examType, boolean isPass,
			int examScore, int passCount, int failCount, int questionNum,
			Set<Answer> answers, long version) {
		super();
		this.id = id;
		this.questions = questions;
		this.bookId = bookId;
		this.name = name;
		this.creationTime = creationTime.toDate();
		this.pictureUrl = pictureUrl;
		this.studentId = studentId;
		this.examType = examType;
		this.isPass = isPass;
		this.examScore = examScore;
		this.passCount = passCount;
		this.failCount = failCount;
		this.questionNum = questionNum;
		this.answers = answers;
		this.version = version;
	}
	
	public ExamDTO(Exam exam,Book book) {
		super();
		this.id = exam.getId();
		this.questions = exam.getQuestions();
		this.bookId = exam.getBookId();
		this.name = book.getName();
		this.creationTime = exam.getCreationTime().toDate();
		this.pictureUrl = book.getPictureUrl();
		this.studentId = exam.getStudentId();
		this.examType = exam.getExamType();
		this.isPass = exam.isPass();
		this.examScore = exam.getExamScore();
		this.passCount = exam.getPassCount();
		this.failCount = exam.getFailCount();
		this.questionNum = exam.getQuestionNum();
		this.answers = exam.getAnswers();
		this.version = exam.getVersion();
	}
	
	public int getPassCount() {
		return passCount;
	}

	public void setPassCount(int pass) {
		this.passCount = pass;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int fail) {
		this.failCount = fail;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreationTime(DateTime creationTime) {
		this.creationTime = creationTime.toDate();
	}
	
	public Date getCreateionTime(){
		return this.creationTime;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public void setExamScore(int examScore) {
		this.examScore = examScore;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public void setVersion(long version) {
		this.version = version;
	}


	public Long getId() {
		return id;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public long getVersion() {
		return version;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public Long getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getBookId() {
		return bookId;
	}

	public ExamType getExamType() {
		return examType;
	}

	public boolean isPass() {
		return isPass;
	}

	public int getExamScore() {
		return examScore;
	}

	public Set<Answer> getSubjectiveAnswers() {
		return answers;
	}

	public int getQuestionNum() {
		return this.questionNum;
	}

	public void update(Set<Answer> answers) {
		this.answers = answers;
	}

	 

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 


	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}
