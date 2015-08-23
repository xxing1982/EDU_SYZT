package com.syzton.sunread.service.region;

import java.util.Map;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.region.SchoolDistrictDTO;
import com.syzton.sunread.model.region.SchoolDistrict;

/**
 * Created b
 * Morgan-Leon on 2015/3/16.
 */
public interface SchoolDistrictService {

    public SchoolDistrict add(SchoolDistrictDTO School,Long id);

    public SchoolDistrict deleteById(Long id) throws NotFoundException;

    public SchoolDistrict update(SchoolDistrictDTO updated) throws NotFoundException;

    public SchoolDistrict findById(Long id) throws NotFoundException;

    Page<SchoolDistrict> findAll(Pageable pageable) throws NotFoundException;
    
    public Map<Integer,String> batchSaveOrUpdateSchoolFromExcel(Sheet sheet);
    
    public Page<SchoolDistrict> searchSchoolDistrictsByName(String name,Pageable pageable);
}
