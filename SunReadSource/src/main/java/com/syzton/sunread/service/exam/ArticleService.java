package com.syzton.sunread.service.exam;

import java.util.List;

import com.syzton.sunread.model.exam.Article;

public interface ArticleService {
	public Article getRandomTrainArticleByLevel(int level);
	
	public List<Article> getTrainArticleByLevel(int level);
	
	public List<Article> getAllTrainArticle();
	
	public Article getRandomTestArticle();
	
	public Article getArticle(long id);
	
	public List<Article> getTestArticle();
	
	public List<Article> getAllArticle();
	
	public void deleteArticle(long id);
	
	public Article addArticle(Article article);
	
	public Article updateArticle(Article article);
	
}
