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
	
	@Column(name = "creation_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime creationTime;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
	  name="exam_question",
	  joinColumns={
			  @JoinColumn(name="exam_id")
	  }, inverseJoinColumns={
			  @JoinColumn(name="question_id")
	  }
	)
	private Set<Question> questions;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="book_id")
	private Book book;
	
	@Enumerated(EnumType.STRING)
	@Column(length=10,nullable=false)
	private ExamType examType;
	
	@Column(name="is_pass",nullable=false)
	private boolean isPass = false;
	
	@Column(name="score")
	private int examScore;
	
	@Column(name="pass_count")
	private int pass;
	
	@Column(name="fail_count")
	private int fail;
	
	@Column(name="question_num")
	private int questionNum = 5;
	
	public enum ExamType{FIRST,SECOND,THIRD}
	
	@OneToMany(cascade={CascadeType.ALL})
	private Set<Answer> answers;
	
	@Version
	private long version;

	public Exam() {

	}

//	public static Builder getBuilder(ExamType type) {
//		return new Builder(type);
//	}

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

 
	
	public int getQuestionNum(){
		return this.questionNum;
	}

	
	public void update(Set<Answer> answers) {
		this.answers = answers;
	}


	
	private void countScore(){
		if(this.examType.equals(ExamType.FIRST)||this.examType.equals(ExamType.SECOND)){
			int total = answers.size();
			for(Answer answer: answers){
				ObjectiveAnswer  oAnswer = (ObjectiveAnswer)answer;
				if(oAnswer.isCorrect()){
					this.pass++;
				}else{
					this.fail++;
				}
			}
			
			this.examScore = 100*this.pass/total;
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
        if(this.book!=null){
        	dto.setBook(this.book.createDTO(this.book));
        }
        dto.setId(this.id);
        dto.setExamScore(this.examScore);
        dto.setExamType(this.examType);
        dto.setPass(this.isPass);
        dto.setPassNum(this.pass);
        dto.setFailNum(this.fail);

        return dto;
    }
	
	 

}
