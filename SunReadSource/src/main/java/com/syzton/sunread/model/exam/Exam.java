package com.syzton.sunread.model.exam;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.Student;

@Entity
@Table(name = "exam")
public class Exam extends AbstractEntity {

	public static final int EXAM_QUESTION = 10;
	
	public static final int EXAM_SUBJECTIVE_QUESTION_PER_TYPE = 2;
	
	public static final int EXAM_CAPACITY_QUESTION_PER_TYPE = 2;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "exam_question", joinColumns = { @JoinColumn(name = "exam_id") }, inverseJoinColumns = { @JoinColumn(name = "question_id") })
	private Set<Question> questions;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "book_id")
	private Book book;
	
	
	@Column(name="student_id")
	private Long studentId;

	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private ExamType examType;

	@Column(name = "is_pass", nullable = false)
	private boolean isPass = false;

	@Column(name = "score")
	private int examScore;

	@Column(name = "pass_count")
	private int passCount;

	@Column(name = "fail_count")
	private int failCount;

	@Column(name = "question_num")
	private int questionNum = 5;
	
	public boolean isFirstPass() {
		return firstPass;
	}

	public void setFirstPass(boolean firstPass) {
		this.firstPass = firstPass;
	}

	public boolean isSecondPass() {
		return secondPass;
	}

	public void setSecondPass(boolean secondPass) {
		this.secondPass = secondPass;
	}

	@Column(name = "first_pass")
	private boolean firstPass=false;
	
	@Column(name = "second_pass")
	private boolean secondPass=false;
	


	public enum ExamType {
		VERIFY, THINK, CAPACITY,SPEED,WORD
	}

	@OneToMany(cascade = { CascadeType.ALL },fetch = FetchType.LAZY)
	private Set<Answer> answers;

	@Version
	private long version;

	public Exam() {

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
		this.creationTime = creationTime;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public void setBook(Book book) {
		this.book = book;
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

	// public static Builder getBuilder(ExamType type) {
	// return new Builder(type);
	// }

	public Long getId() {
		return id;
	}

	public DateTime getCreationTime() {
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

	public Book getBook() {
		return book;
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

	public static Builder getBuilder(ExamType type) {
		return new Builder(type);
	}

	public static class Builder {

		private Exam built;

		public Builder(ExamType type) {
			built = new Exam();
			built.examType = type;

		}

		public Exam build() {
			return built;
		}

		public Builder answers(Set<Answer> answers) {
			built.answers = answers;
			return this;
		}

	}


}
