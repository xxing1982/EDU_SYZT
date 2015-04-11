package com.syzton.sunread.dto.exam;

import com.syzton.sunread.model.exam.Exam;

public class VerifiedExamDTO extends ExamDTO {
	
	private int coin;

	private int point;
	
	public VerifiedExamDTO(Exam exam) {
		super(exam);
		this.coin = exam.getBook().getCoin();
		this.point = exam.getBook().getPoint();
		 
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
}
