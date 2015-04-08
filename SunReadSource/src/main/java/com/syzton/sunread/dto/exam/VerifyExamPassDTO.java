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
	
	public VerifyExamPassDTO(List<ExamDTO> exams,int passRate){
		this.examDTOs = exams;
		this.passRate = passRate;
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

}
