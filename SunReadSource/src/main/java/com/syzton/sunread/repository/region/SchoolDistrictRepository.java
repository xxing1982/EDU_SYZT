package com.syzton.sunread.repository.region;


import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.region.SchoolDistrict;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface SchoolDistrictRepository extends JpaRepository<SchoolDistrict,Long> {
	public SchoolDistrict findByName(String name);
}
