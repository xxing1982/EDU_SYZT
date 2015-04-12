package com.syzton.sunread.dto.exam;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.model.exam.Exam.ExamType;

public class VerifyExamPassDTO {

	private List<ExamDTO> examDTOs;

	private int passRate;
	
	private String firstExamTime;
	
	public VerifyExamPassDTO(List<ExamDTO> exams,int passRate,String firstExamTime){
		this.examDTOs = exams;
		this.passRate = passRate;
		this.firstExamTime = firstExamTime;
	}

	public List<ExamDTO> getExamDTOs() {
		return examDTOs;
	}

	public void setExamDTOs(List<ExamDTO> examDTOs) {
		this.examDTOs = examDTOs;
	}

	public int getPassRate() {
		return passRate;
	}

	public void setPassRate(int passRate) {
		this.passRate = passRate;
	}

	public String getFirstExamTime() {
		return firstExamTime;
	}

	public void setFirstExamTime(String firstExamTime) {
		this.firstExamTime = firstExamTime;
	}

}
