package com.syzton.sunread.repository.exam;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.Article;

public interface ArticleRepository extends JpaRepository<Article,Long> {
	
	List<Article> findByIsTrainingAndLevel(boolean isTraining,int level);
	
	List<Article> findByIsTraining(boolean isTraining);
	
}
