package com.syzton.sunread.repository.organization;

import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.model.organization.School;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface SchoolRepository extends JpaRepository<School,Long> {
	public School findByNameAndEduGroup(String name,EduGroup group);
}
