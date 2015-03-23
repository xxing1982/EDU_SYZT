package com.syzton.sunread.service.region;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.region.RegionDTO;
import com.syzton.sunread.model.region.Region;


/**
 * @author Morgan-Leon
 * @Date 2015年3月24日
 * 
 */
public interface RegionService {

    public RegionDTO add(RegionDTO added);
    
    public Region deleteById(long id);

    public RegionDTO update(RegionDTO updated);

    public Region findOne(Long id) ;

    Page<Region> findAll(Pageable pageable);
}
