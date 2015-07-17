package com.syzton.sunread.dto.exam;

import java.util.List;

import com.syzton.sunread.model.exam.ObjectiveQuestion;

public class VerifyPaperDTO {
 
		private List<ObjectiveQuestion> questions;
		private String code;
		private String message;
		
		public List<ObjectiveQuestion> getQuestions() {
			return questions;
		}
		public void setQuestions(List<ObjectiveQuestion> questions) {
			this.questions = questions;
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
