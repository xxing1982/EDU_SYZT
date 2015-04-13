package com.syzton.sunread.dto.exam;


import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveQuestion;

 
public class SubjectiveAnswerDTO {
	protected SubjectiveQuestion question;
	
	protected Long studentId;
	
	private String content;

	public Answer FromOTD(){
		SubjectiveAnswer answer = new SubjectiveAnswer();
		answer.setContent(content);
		answer.setQuestion(question);
		answer.setStudentId(studentId);
		return answer;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public SubjectiveQuestion getQuestion() {
		return question;
	}

	public void setQuestion(SubjectiveQuestion question) {
		this.question = question;
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
