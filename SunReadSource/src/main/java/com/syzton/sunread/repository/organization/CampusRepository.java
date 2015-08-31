package com.syzton.sunread.repository.organization;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	@Query("select c from Campus c where c.schoolDistrict.id = (:schoolDistrictId)")
	public List<Campus> findBySchoolDistrictId(@Param("schoolDistrictId")Long schoolDistrictId);
	
	@Query("select c from Campus c where c.eduGroup.id = (:eduGroupId)")
	public List<Campus> findByEduGroupId(@Param("eduGroupId")Long eduGroupId);
}
