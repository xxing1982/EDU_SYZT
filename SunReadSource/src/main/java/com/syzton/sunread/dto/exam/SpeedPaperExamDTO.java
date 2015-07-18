package com.syzton.sunread.dto.exam;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.SpeedQuestion;

public class SpeedPaperExamDTO {

	private Set<SpeedQuestion> questions;

	private long articleId;
 
	private Long studentId;

	private ExamType examType;
	
	private int time;
	 
	private Set<SpeedAnswerDTO> answers;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public Exam fromOTD(){
		Exam exam = new Exam();
		Set<Question> set = new HashSet<Question>();
		set.addAll(questions);
		exam.setQuestions(set);
		Set<Answer> answerSet = new HashSet<Answer>();
		for(SpeedAnswerDTO answer : answers){
			answerSet.add(answer.FromOTD());
		}
		exam.setAnswers(answerSet);
		exam.setArticleId(articleId);
		exam.setExamType(examType);
		exam.setStudentId(studentId);
		exam.setQuestionNum(set.size());
		return exam;
	}
	
	public Set<SpeedQuestion> getQuestions() {
		return questions;
	}


	public void setQuestions(Set<SpeedQuestion> questions) {
		this.questions = questions;
	}


	public Long getStudentId() {
		return studentId;
	}


	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}


	public ExamType getExamType() {
		return examType;
	}


	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public Set<SpeedAnswerDTO> getAnswers() {
		return answers;
	}


	public void setAnswers(Set<SpeedAnswerDTO> answers) {
		this.answers = answers;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	 
}
