package com.syzton.sunread.repository.region;

import com.syzton.sunread.model.region.RegionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.region.Region;

/**
 * @author Morgan-Leon
 * @Date 2015年3月23日
 * 
 */
public interface RegionRepository extends JpaRepository<Region, Long>{

    Region findByNameAndRegionTypeAndParent(String name,RegionType regionType,Region parent);

    Page<Region> findByRegionType(Pageable pageable,RegionType regionType);
}
