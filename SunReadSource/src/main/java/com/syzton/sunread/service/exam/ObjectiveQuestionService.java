package com.syzton.sunread.service.exam;

import java.util.Map;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.model.exam.CapacityQuestion;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Option;
import com.syzton.sunread.model.exam.SpeedQuestion;

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
	
	public Page<ObjectiveQuestion> searchObjectiveQuestionByTopic(String topic,Pageable pageable);
	
	public CapacityQuestion addCapacityQuestion(CapacityQuestion added);

	public CapacityQuestion deleteCapacityQuestionById(Long id)throws NotFoundException;

	public CapacityQuestion findCapacityQuestionById(Long id) throws NotFoundException ;

	public CapacityQuestion updateCapacityQuestion(CapacityQuestion updated) throws NotFoundException;
	
	public Map<Integer,String> batchSaveOrUpdateObjectiveQuestionFromExcel(Sheet sheet);

	SpeedQuestion addSpeedQuestion(SpeedQuestion added);

	SpeedQuestion findSpeedQuestionById(Long id) throws NotFoundException;

	SpeedQuestion deleteSpeedQuestionById(Long id) throws NotFoundException;

	SpeedQuestion updateSpeedQuestion(SpeedQuestion updated)throws NotFoundException;

	Page<SpeedQuestion> findAllSpeedQuestion(Pageable pageable)
			throws NotFoundException;
	
}
