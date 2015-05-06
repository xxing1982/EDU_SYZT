package com.syzton.sunread.service.organization;

import java.util.Map;

import com.syzton.sunread.dto.organization.SchoolDTO;
import com.syzton.sunread.model.organization.School;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface SchoolService {

    public School add(SchoolDTO School,Long id);

    public School deleteById(Long id) throws NotFoundException;

    public School update(SchoolDTO updated) throws NotFoundException;

    public School findById(Long id) throws NotFoundException;

    Page<School> findAll(Pageable pageable) throws NotFoundException;
    
    public Map<Integer,String> batchSaveOrUpdateSchoolFromExcel(Sheet sheet);
}
