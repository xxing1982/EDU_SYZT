package com.syzton.sunread.repository.organization;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.dto.campus.CampusOrderDTO;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.organization.ClazzStatistic;
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

	@Query(value = "SELECT new com.syzton.sunread.dto.campus.CampusOrderDTO( "
							+ "c.campus.name,"
							+ "avg(c.clazzStatistic.totalPoints),"
							+ "avg(c.clazzStatistic.totalReads), "
							+ "avg(c.clazzStatistic.totalCoin),"
							+ "avg(c.clazzStatistic.totalNote) )"
		 + "FROM Clazz c "
		 + "WHERE c.campus.schoolDistrict.id = :schoolDistrictId AND c.grade = :grade "
		 + "GROUP BY c.campus.name ")
	public List<CampusOrderDTO> hotCampusesInGradeAndSchoolDistrict(@Param("grade")int grade, @Param("schoolDistrictId")long schoolDistrictId);

	@Query(value = "SELECT new com.syzton.sunread.dto.campus.CampusOrderDTO( "
							+ "c.campus.name,"
							+ "avg(c.clazzStatistic.totalPoints),"
							+ "avg(c.clazzStatistic.totalReads), "
							+ "avg(c.clazzStatistic.totalCoin),"
							+ "avg(c.clazzStatistic.totalNote) )"
		 + "FROM Clazz c "
		 + "WHERE c.campus.eduGroup.id = :eduGroupId AND c.grade = :grade "
		 + "GROUP BY c.campus.name ")
	public List<CampusOrderDTO> hotCampusesInGradeAndEduGroup(@Param("grade")int grade, @Param("eduGroupId")long eduGroupId);
}
