package com.syzton.sunread.repository.region;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.organization.EduGroup;
import com.syzton.sunread.model.region.SchoolDistrict;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface SchoolDistrictRepository extends JpaRepository<SchoolDistrict,Long> {
	public SchoolDistrict findByName(String name);
	
	public Page<SchoolDistrict> findByNameContaining(String name,Pageable pageable);
	
}
