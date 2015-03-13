package com.syzton.sunread.service.exam;

import java.util.List;

import com.syzton.sunread.dto.exam.ExamDTO;
import com.syzton.sunread.dto.exam.QuestionDTO;
import com.syzton.sunread.model.exam.Exam;
import com.syzton.sunread.model.exam.Question;

public interface QuestionService {
	
	public Question add(QuestionDTO added);

	public Question deleteById(Long id);

	public List<Question> findAll();

	public Question findById(Long id);

	public Question update(QuestionDTO updated);
}
