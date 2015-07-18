package com.syzton.sunread.service.exam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.exception.exam.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;

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
