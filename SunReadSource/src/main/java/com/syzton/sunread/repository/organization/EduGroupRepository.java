package com.syzton.sunread.repository.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.organization.EduGroup;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface EduGroupRepository extends JpaRepository<EduGroup,Long> {
	public EduGroup findByName(String name);
}
