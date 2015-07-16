package com.syzton.sunread.controller.exam;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.dto.exam.ArticleMapDTO;
import com.syzton.sunread.dto.exam.SpeedDTO;
import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.exam.Article;
import com.syzton.sunread.model.exam.SpeedQuestion;
import com.syzton.sunread.service.exam.ArticleService;
import com.syzton.sunread.service.exam.ExamService;

@Controller
@RequestMapping(value = "/api")
public class ArticleController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ArticleController.class);

	private ArticleService service;
	
	private ExamService examService;

	@Autowired
	public ArticleController(ArticleService articleService,ExamService examService) {
		this.service = articleService;
		this.examService = examService;
	}

	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Article getArticleById(@PathVariable("id") Long id)
			throws NotFoundException {

		Article article = service.getArticle(id);

		return article;
	}

	@RequestMapping(value = "/article/test/question", method = RequestMethod.GET)
	@ResponseBody
	public SpeedDTO getRandomTestArticleWithQuestion() throws NotFoundException {

		Article article = service.getRandomTestArticle();
		
		List<SpeedQuestion> questions = examService.takeSpeedTest(article.getId());
		SpeedDTO dto = new SpeedDTO();
		dto.setArticle(article);
		dto.setQuestions(questions);
		return dto;
	}
	
	@RequestMapping(value = "/article/test", method = RequestMethod.GET)
	@ResponseBody
	public Article getRandomTestArticle() throws NotFoundException {

		Article article = service.getRandomTestArticle();
		return article;
	}
	
	

	@RequestMapping(value = "/article/train", method = RequestMethod.GET)
	@ResponseBody
	public List<ArticleMapDTO> getTrainArticleList() throws NotFoundException {

		List<Article> articles = service.getAllTrainArticle();
		List<ArticleMapDTO> dtos = new ArrayList<ArticleMapDTO>();
		for (int i = 0; i < articles.size(); i++) {
			ArticleMapDTO dto = new ArticleMapDTO();
			Article article = articles.get(i);
			dto.setId(article.getId());
			dto.setTopic(article.getTopic());
			dtos.add(dto);
		}

		return dtos;
	}

	@RequestMapping(value = "/article/train/{level}", method = RequestMethod.GET)
	@ResponseBody
	public List<ArticleMapDTO> getTrainArticleList(
			@PathVariable("level") int level) throws NotFoundException {

		List<Article> articles = service.getTrainArticleByLevel(level);
		List<ArticleMapDTO> dtos = new ArrayList<ArticleMapDTO>();
		for (int i = 0; i < articles.size(); i++) {
			ArticleMapDTO dto = new ArticleMapDTO();
			Article article = articles.get(i);
			dto.setId(article.getId());
			dto.setTopic(article.getTopic());
			dtos.add(dto);
		}

		return dtos;
	}

	@RequestMapping(value = "/article", method = RequestMethod.POST)
	@ResponseBody
	public Article add(@Valid @RequestBody Article article) {

		Article added = service.addArticle(article);

		return added;
	}
	
	@RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteById(@PathVariable("id") Long id) throws QuestionNotFoundExcepiton {

        service.deleteArticle(id);
    }
	
	@RequestMapping(value = "/article", method = RequestMethod.PUT)
    @ResponseBody
    public Article update(@Valid @RequestBody Article  article) throws QuestionNotFoundExcepiton {

        Article updated = service.updateArticle(article);

        return updated;
    }
}
