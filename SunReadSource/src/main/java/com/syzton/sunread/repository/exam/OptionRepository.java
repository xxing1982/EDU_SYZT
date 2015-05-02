package com.syzton.sunread.repository.exam;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.Option;

public interface OptionRepository  extends JpaRepository<Option, Long> {
	
	public Option findByContent(String content);
}
