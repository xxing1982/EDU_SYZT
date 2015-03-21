package com.syzton.sunread.service.education_system;

import com.syzton.sunread.dto.education_system.GradeDTO;
import com.syzton.sunread.model.education_system.Grade;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface GradeService {

    public Grade add(GradeDTO Grade);

    public Grade deleteById(Long id) throws NotFoundException;

    public Grade update(GradeDTO updated) throws NotFoundException;

    public Grade findById(Long id) throws NotFoundException;

    Page<Grade> findAll(Pageable pageable) throws NotFoundException;
}
