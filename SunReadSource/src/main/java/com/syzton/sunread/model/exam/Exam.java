package com.syzton.sunread.model.exam;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.model.book.Book;

@Entity
@Table(name = "exam")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// TODO
	public static final int EXAM_QUESTION = 10;
	public static final int EXAM_SUBJECTIVE_QUESTION_PER_TYPE = 2;

	@Column(name = "creation_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationTime;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "exam_question", joinColumns = { @JoinColumn(name = "exam_id") }, inverseJoinColumns = { @JoinColumn(name = "question_id") })
	private Set<Question> questions;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;

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

	public enum ExamType {
		VERIFY, THINK, CAPACITY
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

	private void countScore() {
		if (this.examType.equals(ExamType.VERIFY)
				|| this.examType.equals(ExamType.CAPACITY)) {
			int total = answers.size();
			for (Answer answer : answers) {
				ObjectiveAnswer oAnswer = (ObjectiveAnswer) answer;
				if (oAnswer.isCorrect()) {
					this.passCount++;
				} else {
					this.failCount++;
				}
			}

			this.examScore = 100 * this.passCount / total;
		}

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

	public ExamDTO createDTO() {
		ExamDTO dto = new ExamDTO();
		// if(this.book!=null){
		// dto.setBook(this.book.createDTO(this.book));
		// }
		dto.setId(this.id);
		dto.setExamScore(this.examScore);
		dto.setExamType(this.examType);
		dto.setPass(this.isPass);
		dto.setPassNum(this.passCount);
		dto.setFailNum(this.failCount);

		return dto;
	}

}