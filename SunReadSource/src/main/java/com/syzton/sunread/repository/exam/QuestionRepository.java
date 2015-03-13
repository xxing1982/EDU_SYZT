package com.syzton.sunread.repository.exam;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.Question;

public interface QuestionRepository<T extends Question,ID extends Serializable> extends JpaRepository<T, ID> {
	
}