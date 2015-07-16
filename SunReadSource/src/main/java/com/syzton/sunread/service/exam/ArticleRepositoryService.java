package com.syzton.sunread.service.exam;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syzton.sunread.model.exam.Article;
import com.syzton.sunread.repository.exam.ArticleRepository;


@Service
public class ArticleRepositoryService implements ArticleService {
	private ArticleRepository repository;
	
	@Autowired
	public ArticleRepositoryService(ArticleRepository repository) {
		this.repository = repository;

	}

	@Override
	public Article getRandomTrainArticleByLevel(int level) {
		List<Article> articles = repository.findByIsTrainingAndLevel(true, level);
		int i = articles.size();
		Random random = new Random();
		if(i>0){
			int z=random.nextInt(i-1);
			return articles.get(z);
		}
		return null;
	}

	@Override
	public Article getArticle(long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Article> getAllTrainArticle() {
		return repository.findByIsTraining(true);
	}

	@Override
	public Article getRandomTestArticle() {
		List<Article> articles = repository.findByIsTraining(false);
		int i = articles.size();
		Random random = new Random();
		if(i>0){
			int z=random.nextInt(i-1);
			return articles.get(z);
		}
		return null;
	}


	@Override
	public List<Article> getTestArticle() {
		return repository.findByIsTraining(false);
	}

	@Override
	public List<Article> getAllArticle() {
		return repository.findAll();
	}

	@Override
	public void deleteArticle(long id) {
		
		repository.delete(repository.findOne(id));;
	}

	@Override
	public Article addArticle(Article article) {
		return repository.save(article);
	}

	@Override
	public Article updateArticle(Article article) {
		Article old = repository.findOne(article.getId());
		old.setAuthor(article.getAuthor());
		old.setContent(article.getContent());
		old.setLevel(article.getLevel());
		old.setTopic(article.getTopic());
		repository.save(old);
		return old;
	}

	@Override
	public List<Article> getTrainArticleByLevel(int level) {
		return repository.findByIsTrainingAndLevel(true, level);
		
	}
}
