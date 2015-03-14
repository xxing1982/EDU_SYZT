package com.syzton.sunread.service.exam;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Question;

@Service
public class ExamRepositoryService implements ExamService{

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

	@Override
	public List<Exam> findAll() {
		// TODO Auto-generated method stub
		return null;
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
