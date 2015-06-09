package com.syzton.sunread.service.region;

import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
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

    public Region add(RegionDTO added);
    
    public void deleteById(long id);

    public Region update(Region updated);

    public Region findOne(Long id) ;

    Page<Region> findAll(Pageable pageable);
    
//    public Map<Integer,String> batchSaveOrUpdateRegionFromExcel(Sheet sheet);
}
