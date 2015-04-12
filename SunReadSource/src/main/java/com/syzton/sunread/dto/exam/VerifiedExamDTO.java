package com.syzton.sunread.dto.exam;

import com.syzton.sunread.model.exam.Exam;

public class VerifiedExamDTO extends ExamDTO {
	
	private int coin;

	private int point;
	
	private String comment;
	
	public VerifiedExamDTO(Exam exam) {
		super(exam);
		this.coin = exam.getBook().getCoin();
		this.point = exam.getBook().getPoint();
	}
	public VerifiedExamDTO(Exam exam,String comment) {
		super(exam);
		this.coin = exam.getBook().getCoin();
		this.point = exam.getBook().getPoint();
		this.comment = comment;
	}
	
	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
