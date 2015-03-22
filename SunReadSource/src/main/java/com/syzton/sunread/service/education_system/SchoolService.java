package com.syzton.sunread.service.education_system;

import com.syzton.sunread.dto.education_system.SchoolDTO;
import com.syzton.sunread.model.education_system.School;

import javassist.NotFoundException;

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
}
