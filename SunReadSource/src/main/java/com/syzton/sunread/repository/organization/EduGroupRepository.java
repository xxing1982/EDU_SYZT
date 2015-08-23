package com.syzton.sunread.repository.organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.organization.EduGroup;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface EduGroupRepository extends JpaRepository<EduGroup,Long> {
	public EduGroup findByName(String name);
	
	public Page<EduGroup> findByNameContaining(String name,Pageable pageable);
}
