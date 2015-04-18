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

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.SubjectiveAnswer;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.user.Student;
import com.syzton.sunread.repository.exam.AnswerRepository;
import com.syzton.sunread.repository.exam.SubjectiveAnswerRepository;
import com.syzton.sunread.repository.exam.SubjectiveQuestionRepository;
import com.syzton.sunread.repository.user.StudentRepository;
import com.syzton.sunread.repository.user.UserRepository;
import com.syzton.sunread.service.user.UserService;

@Service
public class SubjectiveAnswerRepositoryService implements SubjectiveAnswerService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SubjectiveAnswerRepositoryService.class);
	
	private SubjectiveAnswerRepository repository;
	
	private StudentRepository studentRepo;
	
	private SubjectiveQuestionRepository questionRepo;
	
	@Autowired
	public SubjectiveAnswerRepositoryService(SubjectiveAnswerRepository repository,StudentRepository studentRepo,SubjectiveQuestionRepository questionRepo) {
		
		this.repository = repository;
		this.studentRepo = studentRepo;
		this.questionRepo = questionRepo;
	}
	
	@Transactional(rollbackFor = { NotFoundException.class })
	@Override
	public Page<SubjectiveAnswer> findAll(Pageable pageable)
			throws NotFoundException {

		Page<SubjectiveAnswer> subjectiveAnswerPages = repository.findAll(pageable);

		return subjectiveAnswerPages;

	}

	@Override
	public Page<SubjectiveAnswer> findOtherPersonAnswer(Long questionId,Long userId,
			Pageable pageable) throws NotFoundException {
		SubjectiveQuestion question = questionRepo.findOne(questionId);
		Student student = studentRepo.findOne(userId);
		Page<SubjectiveAnswer> subjectiveAnswerPages = repository.findByQuestionAndCampusIdAndIdNot(question,student.getCampusId(),userId,pageable);
		return subjectiveAnswerPages;
	}
	
	@Transactional
	@Override
	public SubjectiveAnswer add(SubjectiveAnswer added) {
		LOGGER.debug("Adding a new Answer entry with information: {}", added);
		return repository.save(added);
	}

	@Transactional(rollbackFor = { AnswerNotFoundException.class })
	@Override
	public SubjectiveAnswer deleteById(Long id) throws NotFoundException {
		LOGGER.debug("Deleting a to-do entry with id: {}", id);

		SubjectiveAnswer deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);

		repository.delete(deleted);
		return deleted;
	}

	 
	@Transactional(readOnly = true, rollbackFor = { AnswerNotFoundException.class })
	@Override
	public SubjectiveAnswer findById(Long id) throws NotFoundException {
		LOGGER.debug("Finding a to-do entry with id: {}", id);

		SubjectiveAnswer found = repository.findOne(id);
		LOGGER.debug("Found to-do entry: {}", found);

		if (found == null) {
			throw new NotFoundException("No to-entry found with id: " + id);
		}

		return found;
	}

	@Override
	public Page<SubjectiveAnswer> findAllByQuestionId(Pageable pageable,
			Long questionId) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
