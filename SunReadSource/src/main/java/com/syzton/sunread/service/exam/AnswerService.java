package com.syzton.sunread.service.exam;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.exam.AnswerDTO;
import com.syzton.sunread.exception.answer.AnswerNotFoundException;
import com.syzton.sunread.model.exam.Answer;
import com.syzton.sunread.todo.model.Todo;

/**
 * 
 * @author XieYi
 *
 */
public interface AnswerService<T extends Answer,DTO extends AnswerDTO> {

	 	public T add(DTO added);

	    public T deleteById(Long id) throws AnswerNotFoundException;

	    public List<T> findAll();

	    public T findById(Long id) throws AnswerNotFoundException;

	    public T update(AnswerDTO updated) throws AnswerNotFoundException;
}
