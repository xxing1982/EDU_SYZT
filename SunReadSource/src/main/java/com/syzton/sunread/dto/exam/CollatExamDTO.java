package com.syzton.sunread.dto.exam;

import com.syzton.sunread.model.exam.Exam;

public class CollatExamDTO {
	private Exam exam;
	private String code;
	private String message;
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
