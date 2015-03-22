package com.syzton.sunread.service.education_system;

import com.syzton.sunread.dto.education_system.EduGroupDTO;
import com.syzton.sunread.model.education_system.EduGroup;

import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */

public interface EduGroupService {

    public EduGroup add(EduGroupDTO EduGroup);

    public EduGroup deleteById(Long id) throws NotFoundException;

    public EduGroup update(EduGroupDTO updated) throws NotFoundException;

    public EduGroup findById(Long id) throws NotFoundException;

    Page<EduGroup> findAll(Pageable pageable) throws NotFoundException;
}
