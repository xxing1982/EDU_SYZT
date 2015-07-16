package com.syzton.sunread.dto.exam;

import java.util.ArrayList;
import java.util.List;

import com.syzton.sunread.model.exam.Article;
import com.syzton.sunread.model.exam.SpeedQuestion;

public class SpeedDTO {
	private Article article;
	
	private List<SpeedQuestion> questions = new ArrayList<SpeedQuestion>();
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<SpeedQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<SpeedQuestion> questions) {
		this.questions = questions;
	}
}
