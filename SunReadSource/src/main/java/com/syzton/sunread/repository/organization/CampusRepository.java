package com.syzton.sunread.repository.organization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.EduGroup;

/**
 * @author Morgan-Leon
 * @Date 2015年4月6日
 * 
 */
public interface CampusRepository extends JpaRepository<Campus,Long> {

	public Campus findByName(String name);
	
//	public Campus findByNameAndSchool(String name,School school);
	public Page<Campus> findByNameContaining(String name,Pageable pageable);
}
