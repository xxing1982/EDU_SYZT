package com.syzton.sunread.repository.exam;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.Answer;

public interface AnswerRepository<T extends Answer,ID extends Serializable> extends JpaRepository<T, ID> {
	
}
