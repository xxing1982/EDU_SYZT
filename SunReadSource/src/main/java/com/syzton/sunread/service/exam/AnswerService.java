package com.syzton.sunread.service.exam;

import java.util.List;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.model.exam.Question;
import com.syzton.sunread.todo.model.Todo;

/**
 * 
 * @author XieYi
 *
 */
public interface AnswerService {

	 	public Answer add(Answer added);

	    public Answer deleteById(Long id) throws AnswerNotFoundException;

		public Page<Answer> findAll(Pageable pageable);

	    public Answer findById(Long id) throws AnswerNotFoundException;

}
