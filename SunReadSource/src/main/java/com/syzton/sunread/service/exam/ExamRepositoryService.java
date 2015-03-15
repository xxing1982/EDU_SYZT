package com.syzton.sunread.service.exam;

import java.util.List;

import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.exam.ExamRepository;
import com.syzton.sunread.service.book.BookRepositoryService;

@Service
public class ExamRepositoryService implements ExamService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExamRepositoryService.class);
	
	private ExamRepository repository;

	@Autowired
	public ExamRepositoryService(ExamRepository repository) {
		this.repository = repository;
	}

	@Override
	public Exam add(ExamDTO added) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exam deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<Exam> findAll(Pageable pageable) throws NotFoundException {

		Page<Exam> bookPages = repository.findAll(pageable);

		return bookPages;

	}

	@Override
	public Exam findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Exam update(ExamDTO updated) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> takeVerifyTest(Long bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> takeAbilityTest(Long bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> takeThinkTest(Long bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int handInVerifyPaper() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int handInAbilityTest() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean handInThinkTest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchAddQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSubjectiveQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateObjectiveQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

}
