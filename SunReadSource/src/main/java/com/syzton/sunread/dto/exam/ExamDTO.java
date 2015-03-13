package com.syzton.sunread.dto.exam;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Exam.ExamType;

public class ExamDTO {
	
	private Long id;

    @NotEmpty
    private String title;
    
    private int examScore;
    
    private BookDTO book;
    
    private List<AnswerDTO> answers;
    
    private boolean isPass;
    
    private ExamType examType;
    
    private int passNum;
    
    private int failNum;
    
    public ExamDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getExamScore() {
		return examScore;
	}

	public void setExamScore(int examScore) {
		this.examScore = examScore;
	}

	public BookDTO getBook() {
		return book;
	}

	public void setBook(BookDTO book) {
		this.book = book;
	}

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public int getPassNum() {
		return passNum;
	}

	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}

	public int getFailNum() {
		return failNum;
	}

	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}

	public  Exam OTD(){
		Exam exam = Exam.getBuilder(examType).build();
    	return exam;
    }  
}
