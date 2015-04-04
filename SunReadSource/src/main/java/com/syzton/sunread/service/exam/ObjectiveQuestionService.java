package com.syzton.sunread.service.exam;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.Question;

public interface ObjectiveQuestionService {
	
	public Page<ObjectiveQuestion> findAll(Pageable pageable) throws NotFoundException;
	
	public ObjectiveQuestion add(ObjectiveQuestion added);

	public ObjectiveQuestion deleteById(Long id)throws NotFoundException;

	public ObjectiveQuestion findById(Long id) throws NotFoundException ;

	public ObjectiveQuestion update(ObjectiveQuestion updated) throws NotFoundException;
	
	public Page<Option> findAllOption(Pageable pageable) throws NotFoundException;
	
	public Option addOption(Option added);

	public Option deleteOptionById(Long id)throws NotFoundException;

	public Option findOptionById(Long id) throws NotFoundException ;

	public Option updateOption(Option updated) throws NotFoundException;
	
	public ObjectiveQuestion setCorrectOption(Long questionId,Long optionId)throws NotFoundException;
	
	public Page<CapacityQuestion> findAllCapacityQuestion(Pageable pageable) throws NotFoundException;
	
	public CapacityQuestion addCapacityQuestion(CapacityQuestion added);

	public CapacityQuestion deleteCapacityQuestionById(Long id)throws NotFoundException;

	public CapacityQuestion findCapacityQuestionById(Long id) throws NotFoundException ;

	public CapacityQuestion updateCapacityQuestion(CapacityQuestion updated) throws NotFoundException;
	
}
